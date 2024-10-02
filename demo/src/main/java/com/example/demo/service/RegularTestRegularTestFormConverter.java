package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.form.RegularTestForm;
import org.springframework.stereotype.Service;

@Service
public class RegularTestRegularTestFormConverter {
    private final SchoolService schoolService;

    public RegularTestRegularTestFormConverter(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public RegularTest RegularTestFormToRegularTest(RegularTestForm regularTestForm) {
        // Fetch the school entity based on the schoolId
        School school = schoolService.fetchById(regularTestForm.getSchoolId());

        // Create a new RegularTest entity and set its properties
        RegularTest regularTest = new RegularTest();
        regularTest.setSchool(school);
        regularTest.setDate(new java.sql.Date(regularTestForm.getDate().getTime()));
        regularTest.setGrade(regularTestForm.getGrade());
        regularTest.setSemester(regularTestForm.getSemester());
        regularTest.setIsMid(regularTestForm.getIsMid());
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
}

//RegularTestとRegularTestFormのコンバーター