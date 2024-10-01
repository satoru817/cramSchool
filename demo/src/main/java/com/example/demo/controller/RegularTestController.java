package com.example.demo.controller;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.form.RegularTestForm;
import com.example.demo.service.RegularTestResultService;
import com.example.demo.service.RegularTestService;
import com.example.demo.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RegularTestController {
    private RegularTestResultService regularTestResultService;
    private RegularTestService regularTestService;
    private SchoolService schoolService;

    public RegularTestController(RegularTestResultService regularTestResultService,
                                 RegularTestService regularTestService){
        this.regularTestResultService = regularTestResultService;
        this.regularTestService = regularTestService;
    }

    //テスト作成画面
    @GetMapping("/regularTestCreate")
    public String createRegularTest(Model model){
        RegularTestForm regularTestForm = new RegularTestForm();
        regularTestForm.RegularTestForm100();//全てに100をデフォルトでセットする。
        regularTestForm.setTerm(getTerm());
        model.addAttribute("regularTestForm",regularTestForm);
        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList", schoolList);
        return "regularTest/createRegularTest";
    }





    //現在の年度を取得する関数
    public Integer getTerm(){
        // 今年の4月1日を取得
        LocalDate thisYearApril1 = LocalDate.now().withMonth(4).withDayOfMonth(1);

        // 今日の日付を取得
        LocalDate today = LocalDate.now();

        int currentYear = LocalDate.now().getYear();

        if(today.isBefore(thisYearApril1)){
            return currentYear -1;
        }else{
            return currentYear;
        }

    }
}
