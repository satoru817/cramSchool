package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestSet;
import com.example.demo.entity.School;
import com.example.demo.dto.RegularTestForm;
import com.example.demo.dto.RegularTestShow;
import com.example.demo.repository.RegularTestSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegularTestConverter {
    private final SchoolService schoolService;
    private final RegularTestSetRepository regularTestSetRepository;

    public RegularTest RegularTestFormToRegularTest(RegularTestForm regularTestForm) {

        RegularTest regularTest = new RegularTest();
        regularTest.setDate(new java.sql.Date(regularTestForm.getDate().getTime()));
        regularTest.setJapanese(regularTestForm.getJapanese());
        regularTest.setMath(regularTestForm.getMath());
        regularTest.setEnglish(regularTestForm.getEnglish());
        regularTest.setScience(regularTestForm.getScience());
        regularTest.setSocial(regularTestForm.getSocial());
        regularTest.setMusic(regularTestForm.getMusic());
        regularTest.setArt(regularTestForm.getArt());
        regularTest.setTech(regularTestForm.getTech());
        regularTest.setPe(regularTestForm.getPe());

        return regularTest;
    }

    public void setDataFromRegularTestFormToRegularTest(RegularTestForm regularTest, RegularTest form){
        form.setDate(new java.sql.Date(regularTest.getDate().getTime()));
        form.setJapanese(regularTest.getJapanese());
        form.setMath(regularTest.getMath());
        form.setEnglish(regularTest.getEnglish());
        form.setScience(regularTest.getScience());
        form.setSocial(regularTest.getSocial());
        form.setMusic(regularTest.getMusic());
        form.setArt(regularTest.getArt());
        form.setTech(regularTest.getTech());
        form.setPe(regularTest.getPe());
    }

    public RegularTestForm fromRegularTestToRegularTestForm(RegularTest regularTest) {
        RegularTestForm form = new RegularTestForm();
        form.setDate(regularTest.getDate() != null ? new java.util.Date(regularTest.getDate().getTime()) : null);

        form.setJapanese(regularTest.getJapanese());
        form.setMath(regularTest.getMath());
        form.setEnglish(regularTest.getEnglish());
        form.setScience(regularTest.getScience());
        form.setSocial(regularTest.getSocial());
        form.setMusic(regularTest.getMusic());
        form.setArt(regularTest.getArt());
        form.setTech(regularTest.getTech());
        form.setPe(regularTest.getPe());
        return form;
    }

    public List<RegularTestShow> regularTestListToRegularTestShowList(List<RegularTest> regularTestList){
        List<RegularTestShow> regularTestShowList = new ArrayList<>();
        for(RegularTest regularTest: regularTestList){
            regularTestShowList.add(regularTestToRegularTestShow(regularTest));
        }
        return regularTestShowList;
    }

    public RegularTestShow regularTestToRegularTestShow(RegularTest regularTest){
        RegularTestShow regularTestShow = new RegularTestShow();
        regularTestShow.setRegularTestId(regularTest.getRegularTestId());
        regularTestShow.setSchoolName(regularTest.getSchool().getName());
        //TODO:regularTestShowに足りない属性をセットする必要がある。Idで取ってくればいい
        Optional<RegularTestSet> optionalRegularTestSet = regularTestSetRepository.findById(regularTest.getRegularTestSet().getId());
        optionalRegularTestSet.ifPresent(regularTestSet -> {
            regularTestShow.setSemester(regularTestSet.getSemester());
            regularTestShow.setTerm(regularTestSet.getTerm());
            regularTestShow.setIsMid(regularTestSet.getIsMid());
            regularTestShow.setGrade(regularTestSet.getGrade());
        });


        return regularTestShow;
    }

    public RegularTestShow regularTestSetToRegularTestShow(RegularTestSet regularTestSet){
        RegularTestShow regularTestShow = new RegularTestShow();
        regularTestShow.setSemester(regularTestSet.getSemester());
        regularTestShow.setTerm(regularTestSet.getTerm());
        regularTestShow.setIsMid(regularTestSet.getIsMid());
        regularTestShow.setGrade(regularTestSet.getGrade());

        return regularTestShow;
    }

}

//RegularTestとRegularTestFormのコンバーター