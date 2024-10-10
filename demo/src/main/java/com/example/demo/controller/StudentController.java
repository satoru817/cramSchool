package com.example.demo.controller;
import com.example.demo.entity.*;
import com.example.demo.repository.StatusRepository;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.example.demo.dto.StudentShow;
import com.example.demo.dto.StudentForm;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudentController {
    private final StudentService studentService;
    private final SchoolService schoolService;
    private final SchoolStudentService schoolStudentService;
    private final TermAndYearService termAndYearService;
    private final StatusService statusService;
    private final StatusStudentService statusStudentService;
    private final StatusRepository statusRepository;


    ArrayList<String> gradeList = new ArrayList<>(Arrays.asList("未就学","小１","小２","小３","小４","小５","小６","中１","中２","中３","高１","高２","高３"));
    //これは順番付きの配列


    //書き換え不要
    @Transactional
    @GetMapping("/studentRegister")
    public String studentRegister_g(Model model) {
        if (!model.containsAttribute("studentForm")) {
            StudentForm studentForm = new StudentForm();
            model.addAttribute("studentForm", studentForm);
        }
        List<Status> statusList = statusService.findAll();
        model.addAttribute("statusList",statusList);

        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList", schoolList);

        return "student/student_register";
    }

    //書き換え済み
    @Transactional
    @PostMapping("/studentRegister")
    public String studentRegister_p(Model model,
                                    @Validated StudentForm studentForm,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {

            Long code = studentForm.getCode();
            Integer schoolId = studentForm.getSchoolId();
            School school = schoolService.fetchById(schoolId);

            //studentオブジェクトの生成とDBへの保存
            Student student = new Student();
            student.setCode(code);
            student.setName(studentForm.getName());


            student.setEl1(termAndYearService.getWhenEnteredElementarySchool(studentForm.getGrade()));

            studentService.save(student);

            //SchoolStudentオブジェクトの生成とDBへの保存
            SchoolStudent schoolStudent = new SchoolStudent();

            schoolStudent.setStudent(student);
            schoolStudent.setSchool(school);
            schoolStudent.setCreatedAt(termAndYearService.minSqlDate);
            schoolStudent.setChangedAt(termAndYearService.maxSqlDate);

            schoolStudentService.save(schoolStudent);

            //StatusStudentオブジェクトの生成とDBへの保存
            StatusStudent statusStudent = new StatusStudent();
            statusStudent.setStudent(student);
            statusStudent.setStatus(statusService.getStatusById(studentForm.getStatusId()));
            statusStudent.setCreatedAt(termAndYearService.minSqlDate);
            statusStudent.setChangedAt(termAndYearService.maxSqlDate);
            statusStudentService.save(statusStudent);

            return "redirect:/studentShow";

        } else {
            System.out.println("there is a error");
            redirectAttributes.addFlashAttribute("studentForm", studentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
                    + "studentForm", bindingResult);

            return "redirect:/studentRegister";
        }
    }

    //TODO:エラーが発生しました。メソッド名：getLast, 例外クラス名：java.util.NoSuchElementException
    @GetMapping("/studentShow")
    public String studentShow_g(Model model) {
        List<Student> studentList = studentService.fetchAll();//こっちは大丈夫そう
        List<StudentShow> studentShows = convertToStudentShowList(studentList);//これを書き換えなければstudentshowを書き換えないと

        model.addAttribute("studentShows", studentShows);

        return "student/student_show";
    }

    //書き換え不要
    @GetMapping("/studentEdit/{id}")
    public String editStudent(@PathVariable(name = "id") Integer id,
                              Model model) {
        if(!model.containsAttribute("id")&&!model.containsAttribute("studentForm")){
            model.addAttribute("id",id);
            Student student = studentService.getById(id);
            StudentForm studentForm = convertStudentToStudentForm(student);//このメソッドをどうにかする
            model.addAttribute("studentForm", studentForm);
        }

        List<Status> statusList = statusService.findAll();
        model.addAttribute("statusList",statusList);

        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList", schoolList);

        return "student/student_edit";
    }


    //これはCSVから初めて生徒情報を読み込むときに使うメソッド。
    //すでに存在する生徒はスルーされる(studentCodeによって）
    @PostMapping("/studentUploadCSV")
    public String uploadCsv(@RequestParam("file") MultipartFile file, Model model,RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a CSV file to upload.");
            return "error"; // Return to an error view
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Skip the first two lines (metadata)
            reader.readLine(); // Skip "生徒マスタ"
            reader.readLine(); // Skip "日付,2024/09/26"

            // Read the CSV records starting from the header
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(',')
                    .withQuote('"')
                    .withFirstRecordAsHeader()  // Use the first line after the skipped lines as header
                    .parse(reader);

            for (CSVRecord record : records) {

                System.out.println("Record: " + record);
                // Access the specific columns by header names
                String studentCode = record.get("生徒コード");
                Long studentCodeL = Long.valueOf(studentCode);
                String grade = record.get("学年");
                Integer gradeI = gradeList.indexOf(grade);
                Integer el1 = termAndYearService.getWhenEnteredElementarySchool(gradeI);
                String schoolName = record.get("在学校名");
                String course = record.get("所属コース");
                String lastName = record.get("生徒名前(姓)");
                String firstName = record.get("生徒名前(名)");


                if(!isAlreadyExists(studentCodeL)){
                    Student student = new Student();
                    student.setCode(studentCodeL);

                    String studentName=lastName + " "+firstName;
                    student.setName(studentName);
                    //学校を取ってきてnullじゃなければ登録で良いkana。nullのときのエラーhandlingをどうしたらいい？
                    //el1をせっとするメソッドが必要
                    student.setEl1(el1);
                    School school = schoolService.findByName(schoolName);
                    School schoolUnknown = schoolService.findByName("不明");

                    SchoolStudent schoolStudent = new SchoolStudent();

                    if(school!=null){
                        //student.setSchool(school);
                        schoolStudent.setSchool(school);
                    }else{
                        //student.setSchool(schoolUnknown);
                        schoolStudent.setSchool(schoolUnknown);
                    }

                    String sStatus;

                    switch(course){
                        case "講習生":
                            sStatus = "講習";
                            break;
                        case "個別指導本科生":
                            sStatus="個別";
                            break;
                        case "体験":
                            sStatus="体験";
                            break;
                        default:
                            sStatus = "本科";
                    }
                    studentService.save(student);

                    //TODO sStatusから対応するstatusを取ってくる。
                    Status status = statusRepository.findByName(sStatus);
                    //TODO StatusStudentをつくって保存
                    StatusStudent statusStudent = new StatusStudent();
                    statusStudent.setStudent(student);
                    statusStudent.setStatus(status);
                    statusStudent.setCreatedAt(termAndYearService.minSqlDate);
                    statusStudent.setChangedAt(termAndYearService.maxSqlDate);
                    statusStudentService.save(statusStudent);

                    schoolStudent.setStudent(student);
                    schoolStudent.setCreatedAt(termAndYearService.minSqlDate);
                    schoolStudent.setChangedAt(termAndYearService.maxSqlDate);
                    schoolStudentService.save(schoolStudent);


                }



            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error processing file: " + e.getMessage());
            return "redirect:/studentShow"; // Return to an error view
        }

        redirectAttributes.addFlashAttribute("success", "File processed successfully!");
        return "redirect:/studentShow"; // Return to a success view
    }

    //書き換え完了?
    @PostMapping("/studentExecEdit/{id}")
    public String DoEdit(@PathVariable(name="id")Integer id,
                         @Validated StudentForm studentForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("id",id);
            redirectAttributes.addFlashAttribute("studentForm",studentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            + Conventions.getVariableName(studentForm),bindingResult);


            return "redirect:/studentEdit/{id}";

        }else{
            Student student = convertStudentFormToStudent(studentForm,id);
            studentService.save(student);//codeの変更やstatusの変更は履歴を残さないでそのままDBに変更をかける。

            //このあとのコードで、以前のschoolstudentオブジェクトの最新のものを更新し、新たにschoolstudentオブジェクトを作成する。
            SchoolStudent schoolStudent = schoolStudentService.getSchoolStudentsByStudentIdOrdered(id).getLast();
            schoolStudent.setChangedAt(termAndYearService.getSqlToday());//これだめ！改修した！
            schoolStudentService.save(schoolStudent);//save the updated SchoolStudent

            SchoolStudent newSchoolStudent = new SchoolStudent();
            newSchoolStudent.setStudent(student);
            newSchoolStudent.setSchool(schoolService.fetchById(studentForm.getSchoolId()));
            newSchoolStudent.setCreatedAt(termAndYearService.getSqlToday());
            newSchoolStudent.setChangedAt(termAndYearService.maxSqlDate);
            schoolStudentService.save(newSchoolStudent);//新しいSchoolStudentオブジェクトをDBに保存する

            StatusStudent  statusStudent = statusStudentService.getStatusStudentByStudentIdOrdered(id).getLast();
            statusStudent.setChangedAt(termAndYearService.getSqlToday());
            statusStudentService.save(statusStudent);

            StatusStudent newStatusStudent = new StatusStudent();
            newStatusStudent.setStudent(student);
            newStatusStudent.setStatus(statusService.getStatusById(studentForm.getStatusId()));
            newStatusStudent.setCreatedAt(termAndYearService.getSqlToday());
            newStatusStudent.setChangedAt(termAndYearService.maxSqlDate);
            statusStudentService.save(newStatusStudent);


            redirectAttributes.addFlashAttribute("editSuccess");
            return "redirect:/studentShow";
        }
    }


    //TODO:ここでエラーが出てる
    public StudentShow convertStudentToStudentShow(Student student){

        String schoolGrade = gradeList.get(termAndYearService.getSchoolGrade(student));
        String status = getStatusNowYouBelongTo(student).getName();//TODO:ここでエラーがでてる。一括CSV登録時にstatus_studentに登録してないでしょ。

        return new StudentShow(student.getId(),student.getName(),student.getCode(),schoolGrade,status,getSchoolNowYouBelongTo(student).getName());


    }

    //書き換え完了
    public List<StudentShow> convertToStudentShowList(List<Student> students){
        List<StudentShow> studentShows = new ArrayList<>();
        for(Student student:students){

            studentShows.add(convertStudentToStudentShow(student));
        }

        return studentShows;
    }

    //書き換え完了
    public StudentForm convertStudentToStudentForm(Student student){
        return new StudentForm(student.getCode(),termAndYearService.getSchoolGrade(student),student.getName(),getStatusNowYouBelongTo(student).getStatusId(),getSchoolNowYouBelongTo(student).getId());
        //現在のstatusを取得するようにしないといけない。
    }

    //書き換え完了
    public Student convertStudentFormToStudent(StudentForm studentForm,Integer id){
        //puts id and convert schoolId to real school instance
        Student student = new Student();
        student.setId(id);
        student.setCode(studentForm.getCode());
        student.setName(studentForm.getName());

        student.setEl1(termAndYearService.getWhenEnteredElementarySchool(studentForm.getGrade()));

        return student;
    }

    public Boolean isAlreadyExists(Long code){
        return !(studentService.findByCode(code)==null);
    }

    //新たに追加したmethod
    public School getSchoolNowYouBelongTo(Student student){
        return schoolStudentService.getSchoolStudentsByStudentIdOrdered(student.getId()).getLast().getSchool();
    }

    public Status getStatusNowYouBelongTo(Student student){
        return statusStudentService.getStatusStudentByStudentIdOrdered(student.getId()).getLast().getStatus();
    }




}