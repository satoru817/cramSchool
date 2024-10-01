package com.example.demo.controller;
import com.example.demo.entity.SchoolStudent;
import com.example.demo.service.SchoolStudentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import com.example.demo.form.StudentShow;
import com.example.demo.form.StudentForm;
import com.example.demo.service.SchoolService;
import com.example.demo.service.StudentService;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {
    private StudentService studentService;
    private SchoolService schoolService;
    private SchoolStudentService schoolStudentService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = "9999-12-31";
    Date biggestDate = dateFormat.parse(strDate);

    Date smallestDate = new Date(0);
    ArrayList<String> gradeList = new ArrayList<>(Arrays.asList("未就学","小１","小２","小３","小４","小５","小６","中１","中２","中３","高１","高２","高３"));
    //これは順番付きの配列

    public StudentController(StudentService studentService, SchoolService schoolService,SchoolStudentService schoolStudentService) throws ParseException {
        this.studentService = studentService;
        this.schoolService = schoolService;
        this.schoolStudentService = schoolStudentService;
    }

    //書き換え不要
    @GetMapping("/studentRegister")
    public String studentRegister_g(Model model) {
        if (!model.containsAttribute("studentForm")) {
            StudentForm studentForm = new StudentForm();
            model.addAttribute("studentForm", studentForm);
        }

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

            Long code = Long.valueOf(studentForm.getCode());
            Integer schoolId = studentForm.getSchoolId();
            School school = schoolService.fetchById(schoolId);

            //studentオブジェクトの生成とDBへの保存
            Student student = new Student();
            student.setCode(code);
            student.setName(studentForm.getName());
            student.setStatus(studentForm.getStatus());

            student.setEl1(getWhenEnteredElementarySchool(studentForm.getGrade()));

            studentService.save(student);

            //SchoolStudentオブジェクトの生成とDBへの保存
            SchoolStudent schoolStudent = new SchoolStudent();

            schoolStudent.setStudent(student);
            schoolStudent.setSchool(school);
            schoolStudent.setCreatedAt(smallestDate);
            schoolStudent.setChangedAt(biggestDate);

            schoolStudentService.save(schoolStudent);

            return "redirect:/studentShow";

        } else {
            System.out.println("there is a error");
            redirectAttributes.addFlashAttribute("studentForm", studentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
                    + "studentForm", bindingResult);

            return "redirect:/studentRegister";
        }
    }

    //書き換え不要
    @GetMapping("/studentShow")
    public String studentShow_g(Model model) {
        List<Student> studentList = studentService.fetchAll();

        List<StudentShow> studentShows = convertToStudentShowList(studentList);//これを書き換えなければ->owatta

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
                Integer el1 = getWhenEnteredElementarySchool(gradeI);
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

                    String status;

                    switch(course){
                        case "講習生":
                            status = "講習";
                            break;
                        case "個別指導本科生":
                            status="個別";
                            break;
                        default:
                            status = "本科";
                    }

                    student.setStatus(status);

                    studentService.save(student);

                    schoolStudent.setStudent(student);
                    schoolStudent.setCreatedAt(smallestDate);


                    schoolStudent.setChangedAt(biggestDate);

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
            studentService.save(student);//codeの変更やstatusの変更は履歴を残さないでそのままDBに変更かける。

            //このあとのコードで、以前のschoolstudentオブジェクトの最新のものを更新し、新たにschoolstudentオブジェクトを作成する。
            SchoolStudent schoolStudent = schoolStudentService.getSchoolStudentsByStudentIdOrdered(id).getLast();
            schoolStudent.setChangedAt(new Date());//現在の日付をChangedAtに登録する。これはうまくいっている
            schoolStudentService.save(schoolStudent);//save the updated SchoolStudent

            SchoolStudent newSchoolStudent = new SchoolStudent();
            newSchoolStudent.setStudent(student);
            newSchoolStudent.setSchool(schoolService.fetchById(studentForm.getSchoolId()));
            newSchoolStudent.setCreatedAt(new Date());
            newSchoolStudent.setChangedAt(biggestDate);
            schoolStudentService.save(newSchoolStudent);//新しいSchoolStudentオブジェクトをDBに保存する

            redirectAttributes.addFlashAttribute("editSuccess");
            return "redirect:/studentShow";
        }
    }

    //Studentのel1から学年(1から12)を取得する
    public Integer getSchoolGrade(Student student){
        // 今年の4月1日を取得
        LocalDate thisYearApril1 = LocalDate.now().withMonth(4).withDayOfMonth(1);

        // 今日の日付を取得
        LocalDate today = LocalDate.now();

        int currentYear = LocalDate.now().getYear();

        int grade;

        if(today.isBefore(thisYearApril1)){
            grade = currentYear - student.getEl1();
        }else{
            grade = currentYear - student.getEl1() +1;
        }

        return grade;
    }

    public StudentShow convertStudentToStudentShow(Student student){

        String schoolGrade = gradeList.get(getSchoolGrade(student));

        return new StudentShow(student.getId(),student.getName(),student.getCode(),schoolGrade,student.getStatus(),getSchoolNowYouBelongTo(student).getName());


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
        return new StudentForm(student.getCode(),getSchoolGrade(student),student.getName(),student.getStatus(),getSchoolNowYouBelongTo(student).getId());

    }

    //書き換え完了
    public Student convertStudentFormToStudent(StudentForm studentForm,Integer id){
        //puts id and convert schoolId to real school instance
        Student student = new Student();
        student.setId(id);
        student.setCode(studentForm.getCode());
        student.setName(studentForm.getName());
        student.setStatus(studentForm.getStatus());

        return student;
    }

    public Boolean isAlreadyExists(Long code){
        return !(studentService.findByCode(code)==null);
    }

    //新たに追加したmethod
    public School getSchoolNowYouBelongTo(Student student){
        return schoolStudentService.getSchoolStudentsByStudentIdOrdered(student.getId()).getLast().getSchool();
    }

    //小学一年生は高校3年生は12の数値から小学校入学時(3/31)時点の西暦を算出する関数
    public int getWhenEnteredElementarySchool(int grade){

        // 今年の4月1日を取得
        LocalDate thisYearApril1 = LocalDate.now().withMonth(4).withDayOfMonth(1);

        // 今日の日付を取得
        LocalDate today = LocalDate.now();

        //現在の年を取得
        int currentYear = LocalDate.now().getYear();

        if(today.isBefore(thisYearApril1)){
            return currentYear - grade;
        }else{
            return currentYear + 1 - grade;
        }
    }


}