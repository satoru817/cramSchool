package com.example.demo.controller;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.form.RegularTestForm;
import com.example.demo.service.*;
import com.example.demo.show.RegularTestShow;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegularTestController {
    private RegularTestResultService regularTestResultService;
    private RegularTestService regularTestService;
    private SchoolService schoolService;
    private TermAndYearService termAndYearService;
    private RegularTestConverter regularTestConverter;

    public RegularTestController(RegularTestResultService regularTestResultService,
                                 RegularTestService regularTestService,
                                 SchoolService schoolService,
                                 TermAndYearService termAndYearService,
                                 RegularTestConverter regularTestConverter){
        this.regularTestResultService = regularTestResultService;
        this.regularTestService = regularTestService;
        this.schoolService = schoolService;
        this.termAndYearService = termAndYearService;
        this.regularTestConverter = regularTestConverter;
    }

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






}
