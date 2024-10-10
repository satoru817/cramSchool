package com.example.demo.controller;

import com.example.demo.dto.SchoolForm;
import com.example.demo.dto.Subjects;
import com.example.demo.entity.*;
import com.example.demo.dto.RegularTestForm;
import com.example.demo.repository.RegularTestResultRepository;
import com.example.demo.repository.RegularTestSetRepository;
import com.example.demo.repository.SchoolStudentRepository;
import com.example.demo.service.*;
import com.example.demo.dto.RegularTestShow;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class RegularTestController {
    private final RegularTestResultService regularTestResultService;
    private final RegularTestResultRepository regularTestResultRepository;
    private final RegularTestService regularTestService;
    private final SchoolService schoolService;
    private final TermAndYearService termAndYearService;
    private final RegularTestConverter regularTestConverter;
    private final StudentService studentService;
    private final RegularTestSetRepository regularTestSetRepository;
    private final SchoolStudentRepository schoolStudentRepository;

    //TODO:全体として、RegularTestSetIdに対応しないといけない。
    //テスト作成画面
    @GetMapping("/regularTestCreate")
    public String createRegularTest(Model model){
        if(!model.containsAttribute("regularTestForm")){
            RegularTestForm regularTestForm = new RegularTestForm();
            regularTestForm.initializeWithPerfectScores();//全てに100をデフォルトでセットする。
            regularTestForm.setDate(termAndYearService.getTodayAsDate());
            model.addAttribute("regularTestForm",regularTestForm);
        }

        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList", schoolList);
        return "regularTest/createRegularTest";
    }

    @PostMapping("/submitRegularTest")
    public String submitRegularTest(@ModelAttribute RegularTestForm regularTestForm,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model){
        System.out.println(regularTestForm);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("regularTestForm", regularTestForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(regularTestForm));
            return "redirect:/regularTestCreate";
        } else {
            System.out.println("Saving RegularTest: " + regularTestForm);
            RegularTest regularTest = regularTestConverter.RegularTestFormToRegularTest(regularTestForm);
            try {
                regularTestService.save(regularTest);
                System.out.println("Saved RegularTest successfully!");
            } catch (Exception e) {
                System.err.println("Error saving RegularTest: " + e.getMessage());
                e.printStackTrace();
            }
            return "redirect:/showAllRegularTest";//ここはおかしい。
        }


    }

    @GetMapping("/showAllRegularTest")
    public String showAllRegularTest(Model model){
        List<RegularTest> regularTestList = regularTestService.fetchAll();
        List<RegularTestShow> regularTestShowList = regularTestConverter.regularTestListToRegularTestShowList(regularTestList);
        model.addAttribute("regularTestShowList",regularTestShowList);
        return "regularTest/showAllRegularTest";
    }
    //TODO:regularTestSetを取ってきて、年度、学年、学期、isMidの情報をviewに渡す。OK
    //TODO:平均くんに紐づけて平均を登録できるようにする必要がある。つまり、regular_test_resultに平均くんの結果を保
    // 存する。すでに平均くんがそのテストに関して存在していたら、その結果を取ってこないと
    // 行けない。OK
    @GetMapping("/regularTestEdit/{id}")
    public String regularTestEdit(@PathVariable("id")Integer regularTestId,Model model){

        RegularTest regularTest = regularTestService.fetchById(regularTestId);
        model.addAttribute("regularTest",regularTest);//学校情報を画面で表示するために使う

        RegularTestResult regularTestResult;
        Student ave = studentService.getById(1); // 平均くん。
        Optional<RegularTestResult> optionalRegularTestResult = regularTestResultService.getRegularTestResultByRegularTestAndStudent(regularTest, ave);

        // 既にaveくんのテスト結果が作られていない時だけ、デフォルトのRegularTestResultインスタンスを作成
        regularTestResult = optionalRegularTestResult.orElseGet(RegularTestResult::new);
        regularTestResult.setStudent(ave);
        regularTestResult.setRegularTest(regularTest);

        model.addAttribute("regularTestResult",regularTestResult);

        Boolean isEdit = true;
        model.addAttribute("isEdit",isEdit);
        model.addAttribute("regularTestId",regularTestId);//これで返ってきた時にテストを見分ける。

        RegularTestSet regularTestSet = regularTest.getRegularTestSet();
        model.addAttribute("regularTestSet",regularTestSet);


        RegularTestForm regularTestForm = regularTestConverter.fromRegularTestToRegularTestForm(regularTest);
        model.addAttribute("regularTestForm",regularTestForm);


        Subjects subjects = new Subjects();
        model.addAttribute("subjects", subjects);



        return "regularTest/createRegularTest";

    }
    @PostMapping("/updateRegularTest/average/{id}")
    public String updateReqularTestAverage(@PathVariable("id")Integer regularTestId,
                                           @ModelAttribute RegularTestResult regularTestResult){
        System.out.println(regularTestResult);
        RegularTest regularTest = regularTestService.fetchById(regularTestId);
        Student ave = studentService.getById(1);

        regularTestResult.setRegularTest(regularTest);
        regularTestResult.setStudent(ave);
        regularTestResultService.save(regularTestResult);
        return "redirect:/showAllRegularTest";//TODO ここは改修が必要
    }

    @PostMapping("/updateRegularTest/{id}")
    public String updateRegularTest(@PathVariable("id")Integer regularTestId,
                                    @ModelAttribute RegularTestForm regularTestForm,
                                    RedirectAttributes redirectAttributes,
                                    Model model){
        System.out.println(regularTestForm);

            //TODO regularTestを更新する。OK
            // またaveのテスト結果も更新する。まずregularTestIdで該当するregularTestを取ってくる必要がある
            // RegularTestResultのIdも取ってくる必要があったそれが出来ていない。そのIdを利用して更新する。input type=hiddenを使えば良いんだ。
            System.out.println("Saving RegularTest: " + regularTestForm);
            RegularTest regularTest = regularTestService.fetchById(regularTestId);
            regularTestConverter.setDataFromRegularTestFormToRegularTest(regularTestForm,regularTest);
            regularTestService.save(regularTest);

            Student ave = studentService.getById(1);



            return "redirect:/showAllRegularTest";//ここはおかしい。



    }
//TODO後で復活させる

//    @GetMapping("/regularTestResultEdit/{id}")
//    public String regularTestResultEdit(@PathVariable("id")Integer regularTestId,
//                                        Model model){
//        RegularTest regularTest = regularTestService.fetchById(regularTestId);
//        //regularTestが実施された日にregularTestが実施された学校に属し、regularTestが実施された学年である生徒を探し出す必要がある。こうする
//        //つまり最新を探すのではなく日付で挟むのは、過去のデータを閲覧する際に使えなくてはならないからである。
//        //List<Student> studentList = studentService.findAllBySchoolAndEl1(regularTest.getSchool(),termAndYearService.getWhenEnteredElementarySchool(regularTest.getGrade()));
//        //List<RegularTestResultForm>のオブジェクトを作成してviewに渡す必要がある。
//        List<Student> studentList = studentService.getStudentsByEl1AndDateRangeAndSchoolId(termAndYearService.getWhenEnteredElementarySchool(regularTest.getGrade()), regularTest.getDate().toLocalDate(),regularTest.getSchool().getId());
//        List<TestResultForm> testResultFormList = new ArrayList<>();
//        for(Student student:studentList){
//            TestResultForm testResultForm = new TestResultForm(student);
//            testResultFormList.add(testResultForm);
//        }
//
//        model.addAttribute("regularTestId",regularTestId);
//        model.addAttribute("testResultFormList",testResultFormList);
//        model.addAttribute("regularTestShow",regularTestConverter.regularTestToRegularTestShow(regularTestService.fetchById(regularTestId)));
//
//        return "test/testResultEdit";
//    }

    @GetMapping("/createRegularTest")
    public String createRegularTests(Model model) {
        List<School> schoolList = schoolService.fetchAll();

        List<SchoolForm> schoolFormList = new ArrayList<>();
        for(School school: schoolList){
            System.out.println(school.getName());
            SchoolForm schoolForm = new SchoolForm();
            schoolForm.setId(school.getId());
            schoolForm.setName(school.getName());
            schoolFormList.add(schoolForm);
        }
        model.addAttribute("schoolFormList", schoolFormList);


        List<Integer> selectedSchoolIds = new ArrayList<>();

        return "regularTest/createRegularTestAllAtOnce";
    }
    @Transactional
    @PostMapping("/doCreateRegularTestSetAndRegularTests")
    public String doCreateRegularTestSetAndRegularTests(@RequestParam List<Integer> selectedSchoolIds,
                                        @RequestParam("grade")Integer grade,
                                        @RequestParam("semester")Integer semester,
                                        @RequestParam("isMid")Integer isMid,
                                        RedirectAttributes redirectAttributes) {
        //エラーがあったらもとのフォームに戻る
        if(grade==null||semester==null||isMid==null||selectedSchoolIds.isEmpty()){
            System.out.println("エラーがありました。");
            redirectAttributes.addFlashAttribute("hasError",true);
            redirectAttributes.addFlashAttribute("selectedSchoolIds",selectedSchoolIds);
            return "redirect:/createRegularTest";
        }else{
            System.out.println("isMidは存在します："+isMid);
            Date sqlToday = termAndYearService.getSqlToday();
            Integer thisTerm = termAndYearService.getTerm();
            //RegularTestSetに登録がなければ作成する
            RegularTestSet regularTestSet = new RegularTestSet();

            if(regularTestSetRepository.findByGradeAndTermAndIsMidAndSemester(grade,thisTerm,isMid,semester)==null){
                System.out.println("if文の中に入っています。");
                regularTestSet.setTerm(thisTerm);
                regularTestSet.setSemester(semester);
                System.out.println("isMidは："+isMid);
                regularTestSet.setIsMid(isMid);
                regularTestSet.setGrade(grade);
                System.out.println("RegularTestSetの状態: " + regularTestSet);

                regularTestSetRepository.save(regularTestSet);
            }
            //エラーがなければテストを作成する。
            // しかし、すでにDBに同じ学年同じ年度、同じ学期、同じ中間か期末の物があったら新規で作ってはならない。
            for (Integer id : selectedSchoolIds) {
                //すでに存在するかどうかの判定
                School school = schoolService.fetchById(id);
                if(regularTestService.getBySchoolAndGradeAndSemesterAndIsMidAndTerm(school,grade,semester,isMid,thisTerm)==null){
                    //存在しなければ新規で作成して保存する
                    //TODO:schoolと学年が一致する生徒に関してはregular_exam_resultを作る。el1を使う
                    RegularTest regularTest = new RegularTest();
                    regularTest.setRegularTestSet(regularTestSet);//新たに追加した
                    regularTest.setSchool(school);
                    regularTest.setDate(sqlToday);
                    regularTestService.save(regularTest);//新たなregularTestを保存

                    List<SchoolStudent> schoolStudentList = schoolStudentRepository.findSchoolStudentBySchoolAndDateAndEl1(school,termAndYearService.getWhenEnteredElementarySchool(grade),termAndYearService.getSqlToday());
                    if(!schoolStudentList.isEmpty()){
                        for(SchoolStudent ss:schoolStudentList){
                            //TODO:ssに対応した全点数nullのRegularTestResultを作成する
                            RegularTestResult regularTestResult = new RegularTestResult();
                            regularTestResult.setRegularTest(regularTest);
                            regularTestResult.setStudent(ss.getStudent());
                            regularTestResultRepository.save(regularTestResult);

                        }
                    }





                }else{

                }
            }

            return "redirect:/showAllRegularTest";

        }




    }







}
