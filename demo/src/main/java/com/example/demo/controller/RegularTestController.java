package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.RegularTestRepository;
import com.example.demo.repository.RegularTestResultRepository;
import com.example.demo.repository.RegularTestSetRepository;
import com.example.demo.repository.SchoolStudentRepository;
import com.example.demo.service.*;
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
import java.time.LocalDate;
import java.util.*;

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
    private final RegularTestRepository regularTestRepository;
    private final RegularTestResultConverter regularTestResultConverter;

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
    @PostMapping("/doCreateRegularTestSetAndRegularTests")
    public String doCreateRegularTestSetAndRegularTest(@RequestParam List<Integer> selectedSchoolIds,
                                                       @RequestParam("grade")Integer grade,
                                                       @RequestParam("semester")Integer semester,
                                                       @RequestParam("isMid")Integer isMid,
                                                       RedirectAttributes redirectAttributes) {
        Date sqlToday = termAndYearService.getSqlToday();
        Integer thisTerm = termAndYearService.getTerm();
        Optional<RegularTestSet> optionalRegularTestSet = regularTestSetRepository.findByGradeAndTermAndIsMidAndSemester(grade, thisTerm, isMid, semester);//TODO こっちは多分大丈夫

        RegularTestSet regularTestSet = new RegularTestSet();
        if (optionalRegularTestSet.isEmpty()) {
            regularTestSet.setGrade(grade);
            regularTestSet.setSemester(semester);
            regularTestSet.setTerm(thisTerm);
            regularTestSet.setIsMid(isMid);
            regularTestSetRepository.save(regularTestSet);
        } else {
            regularTestSet = optionalRegularTestSet.get();
        }

        for (Integer id : selectedSchoolIds) {
            School school = schoolService.fetchById(id);
            //TODO ここが論理的におかしい。regularTestRepositoryにメソッドを書きなおす必要がある。書きなおした
            //TODO　今はテスト期間中の短い期間の転校は反映できない。
            Optional<RegularTest> optionalRegularTest = regularTestRepository.getBySchoolAndRegularTestSet(school, regularTestSet);
            //Optional<RegularTest> optionalRegularTest = regularTestService.getBySchoolAndGradeAndSemesterAndIsMidAndTerm(school,grade,semester,isMid,thisTerm);//こっちがダメっぽい
            if (optionalRegularTest.isEmpty()) {//
                RegularTest regularTest = new RegularTest();
                regularTest.setRegularTestSet(regularTestSet);//新たに追加した
                regularTest.setSchool(school);
                regularTest.setDate(sqlToday);
                regularTestService.save(regularTest);//TODO OK
                //TODO findSchoolStudentBySchoolAndDateAndEl1を書きなおす必要がある。
                // このメソッドを書きなおす必要があるらしい。
                System.out.println(school.getName());//MEMO ここまで来てる
                System.out.println(termAndYearService.getWhenEnteredElementarySchoolForJuniorHighSchoolStudent(grade));//TODO この関数が間違ってる。->直した

                //TODO 真下のメソッドが循環参照を生んでいるようだ。一旦なくす。
                //TODO 本当はここで対応する全員のRegularTestResultを作成したかった。
                //TODO JPQLを使うと循環参照を生んでそれが難しそうだから、ネイティブクエリでstudentのidを取ってきて
                // Studentを作成して、何とかしようかな。、->student_idだけ取ってくるようにしたら、循環参照は消えた
                System.out.println("schoolId: "+school.getId());
                System.out.println("el1: "+termAndYearService.getWhenEnteredElementarySchoolForJuniorHighSchoolStudent(grade));
                System.out.println("today: "+termAndYearService.getSqlToday());
                List<Integer> studentIdList = schoolStudentRepository.findSchoolStudentBySchoolAndDateAndEl1(school.getId(),termAndYearService.getWhenEnteredElementarySchoolForJuniorHighSchoolStudent(grade), termAndYearService.getSqlToday());

                System.out.println("schoolStudentList:" + studentIdList);
                if(!studentIdList.isEmpty()){
                    for(Integer studentId:studentIdList){
                        System.out.println("SchoolStudent:" + studentId);
                        //TODO:ssに対応した全点数nullのRegularTestResultを作成する
                        RegularTestResult regularTestResult = new RegularTestResult();
                        regularTestResult.setRegularTest(regularTest);
                        regularTestResult.setStudent(studentService.getById(studentId));
                        regularTestResultRepository.save(regularTestResult);

                    }
                }
            }
            }
            return "redirect:/showAllRegularTest";




    }

    @GetMapping("/showAllRegularTestInChunks")
    public String showAllRegularTestInChunks(Model model){
        List<RegularTestSet> regularTestSetList = regularTestSetRepository.findAll();
        model.addAttribute("regularTestSetList",regularTestSetList);
        return "/regularTest/showAllRegularTestInChunks";
    }



    //TODO:とりあえず学校ごとに成績入力する画面を作る
    // 成績入力画面に学校名、年度、学期、学年、isMidを出したいー＞regulartestshowを渡そう。
    @GetMapping("/regularTestResultEdit/{id}")
    public String regularTestResultEdit(@PathVariable("id")Integer regularTestId,
                                        Model model){
        List<RegularTestResult> regularTestResultList = regularTestResultRepository.findByRegularTestId(regularTestId);
        List<RegularTestResultShow> regularTestResultShowList = regularTestResultConverter.convertRegularTestResultsToRegularTestResultShows(regularTestResultList);
        model.addAttribute("regularTestResultShowList",regularTestResultShowList);
        model.addAttribute("regularTestId",regularTestId);
        RegularTest regularTest = regularTestService.fetchById(regularTestId);
        RegularTestShow  regularTestShow = regularTestConverter.regularTestToRegularTestShow(regularTest);
        model.addAttribute("regularTestShow",regularTestShow);

        return "regularTest/editResultBySchool";
    }

    @PostMapping("/RegularTestResultUpdate")
    public String regularTestResultUpdate(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        Integer regularTestId = Integer.valueOf(params.get("regularTestId"));

        for (String key : params.keySet()) {
            if (key.startsWith("id_")) {
                Integer id = Integer.valueOf(params.get(key));
                RegularTestResult result = regularTestResultRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test result ID: " + id));

                // 科目ごとの点数を更新
                result.setJapanese(Integer.valueOf(params.get("japanese_" + id)));
                result.setMath(Integer.valueOf(params.get("math_" + id)));
                result.setEnglish(Integer.valueOf(params.get("english_" + id)));
                result.setScience(Integer.valueOf(params.get("science_" + id)));
                result.setSocial(Integer.valueOf(params.get("social_" + id)));
                result.setMusic(Integer.valueOf(params.get("music_" + id)));
                result.setArt(Integer.valueOf(params.get("art_" + id)));
                result.setTech(Integer.valueOf(params.get("tech_" + id)));
                result.setPe(Integer.valueOf(params.get("pe_" + id)));

                // 保存
                regularTestResultRepository.save(result);
            }
        }

        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "Results updated successfully!");

        // 更新後の結果一覧を再表示
        return "redirect:/regularTestResultEdit/" + regularTestId; // regularTestIdを使用
    }

    @GetMapping("/regularTestResultChunkEdit/{id}")
    public String regularTestResultChunkEdit(@PathVariable("id")Integer regularTestSetId,
                                        Model model){
        List<RegularTestResult> regularTestResultList = regularTestResultRepository.findByRegularTestSetId(regularTestSetId);
        List<RegularTestResultShow> regularTestResultShowList = regularTestResultConverter.convertRegularTestResultsToRegularTestResultShows(regularTestResultList);
        model.addAttribute("regularTestResultShowList",regularTestResultShowList);
        model.addAttribute("regularTestSetId",regularTestSetId);
        Optional<RegularTestSet> optionalRegularTestSet = regularTestSetRepository.findById(regularTestSetId);
        optionalRegularTestSet.ifPresent(regularTestSet -> {
            model.addAttribute("regularTestSet",regularTestSet);
            RegularTestShow regularTestShow = regularTestConverter.regularTestSetToRegularTestShow(regularTestSet);
            model.addAttribute("regularTestShow",regularTestShow);
        });
        //TODO:RegularTestShowを渡さないといけない。学校名はいらない。nullでいい。->済


        return "regularTest/editResultAtOnce";
    }

    @PostMapping("/RegularTestResultUpdateAtOnce")
    public String regularTestResultUpdateAtOnce(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        Integer regularTestSetId = Integer.valueOf(params.get("regularTestSetId"));

        for (String key : params.keySet()) {
            if (key.startsWith("id_")) {
                Integer id = Integer.valueOf(params.get(key));
                RegularTestResult result = regularTestResultRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test result ID: " + id));

                // 科目ごとの点数を更新
                result.setJapanese(Integer.valueOf(params.get("japanese_" + id)));
                result.setMath(Integer.valueOf(params.get("math_" + id)));
                result.setEnglish(Integer.valueOf(params.get("english_" + id)));
                result.setScience(Integer.valueOf(params.get("science_" + id)));
                result.setSocial(Integer.valueOf(params.get("social_" + id)));
                result.setMusic(Integer.valueOf(params.get("music_" + id)));
                result.setArt(Integer.valueOf(params.get("art_" + id)));
                result.setTech(Integer.valueOf(params.get("tech_" + id)));
                result.setPe(Integer.valueOf(params.get("pe_" + id)));

                // 保存
                regularTestResultRepository.save(result);
            }
        }

        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "Results updated successfully!");

        // 更新後の結果一覧を再表示
        return "redirect:/regularTestResultChunkEdit/" + regularTestSetId; // regularTestIdを使用
    }


}


