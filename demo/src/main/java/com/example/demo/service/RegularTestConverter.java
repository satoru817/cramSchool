package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.dto.RegularTestForm;
import com.example.demo.show.RegularTestShow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegularTestConverter {
    private final SchoolService schoolService;

    public RegularTestConverter(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public RegularTest RegularTestFormToRegularTest(RegularTestForm regularTestForm) {
        // Fetch the school entity based on the schoolId
        School school = schoolService.fetchById(regularTestForm.getSchoolId());

        // Create a new RegularTest entity and set its properties
        RegularTest regularTest = new RegularTest();
        regularTest.setSchool(school);
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

    public RegularTestForm fromRegularTestToRegularTestForm(RegularTest regularTest) {
        RegularTestForm form = new RegularTestForm();
        form.setSchoolId(regularTest.getSchool() != null ? regularTest.getSchool().getId() : null); // Assuming School has a method getSchoolId()
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

        return regularTestShow;
    }

}

//RegularTestとRegularTestFormのコンバーター