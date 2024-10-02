package com.example.demo.controller;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.form.RegularTestForm;
import com.example.demo.service.*;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RegularTestController {
    private RegularTestResultService regularTestResultService;
    private RegularTestService regularTestService;
    private SchoolService schoolService;
    private TermAndYearService termAndYearService;
    private RegularTestRegularTestFormConverter regularTestRegularTestFormConverter;

    public RegularTestController(RegularTestResultService regularTestResultService,
                                 RegularTestService regularTestService,
                                 SchoolService schoolService,
                                 TermAndYearService termAndYearService,
                                 RegularTestRegularTestFormConverter regularTestRegularTestFormConverter){
        this.regularTestResultService = regularTestResultService;
        this.regularTestService = regularTestService;
        this.schoolService = schoolService;
        this.termAndYearService = termAndYearService;
        this.regularTestRegularTestFormConverter = regularTestRegularTestFormConverter;
    }

    //テスト作成画面
    @GetMapping("/regularTestCreate")
    public String createRegularTest(Model model){
        if(!model.containsAttribute("regularTestForm")){
            RegularTestForm regularTestForm = new RegularTestForm();
            regularTestForm.initializeWithPerfectScores();//全てに100をデフォルトでセットする。
            regularTestForm.setDate(termAndYearService.getTodayAsLocalDate());
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
            RegularTest regularTest = regularTestRegularTestFormConverter.RegularTestFormToRegularTest(regularTestForm);
            try {
                regularTestService.save(regularTest);
                System.out.println("Saved RegularTest successfully!");
            } catch (Exception e) {
                System.err.println("Error saving RegularTest: " + e.getMessage());
                e.printStackTrace();
            }
            return "redirect:/registerSchool";
        }


    }






}
