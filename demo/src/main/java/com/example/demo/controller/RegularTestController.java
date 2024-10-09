package com.example.demo.controller;

import com.example.demo.dto.RegularTestForm1;
import com.example.demo.dto.SchoolForm;
import com.example.demo.entity.*;
import com.example.demo.dto.RegularTestForm;
import com.example.demo.form.TestResultForm;
import com.example.demo.repository.RegularTestResultRepository;
import com.example.demo.repository.RegularTestSetRepository;
import com.example.demo.repository.SchoolStudentRepository;
import com.example.demo.service.*;
import com.example.demo.show.RegularTestShow;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public String submitRegularTest(@Validated RegularTestForm regularTestForm,
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

    @GetMapping("/regularTestEdit/{id}")
    public String regularTestEdit(@PathVariable("id")Integer id,Model model){
        Integer isEdit = 1;
        model.addAttribute("isEdit",isEdit);
        model.addAttribute("regularTestId",id);//これで見分ける。

        RegularTest regularTest = regularTestService.fetchById(id);
        RegularTestForm regularTestForm = regularTestConverter.fromRegularTestToRegularTestForm(regularTest);

        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList",schoolList);
        model.addAttribute("regularTestForm",regularTestForm);

        return "regularTest/createRegularTest";

    }

    @PostMapping("/updateRegularTest/{id}")
    public String updateRegularTest(@PathVariable("id")Integer id,
                                    @Validated RegularTestForm regularTestForm,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model){
        System.out.println(regularTestForm);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("isEdit",1);
            redirectAttributes.addFlashAttribute("id",id);
            redirectAttributes.addFlashAttribute("regularTestForm", regularTestForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(regularTestForm));
            return "redirect:/regularTestCreate";
        } else {
            System.out.println("Saving RegularTest: " + regularTestForm);
            RegularTest regularTest = regularTestConverter.RegularTestFormToRegularTest(regularTestForm);
            regularTest.setRegularTestId(id);
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

        return "regularTest/createRegularTest1"; // Thymeleafテンプレート名
    }
    @Transactional
    @PostMapping("/submitSchoolSelection")
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
