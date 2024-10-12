package com.example.demo.service;

import com.example.demo.dto.RegularTestResultShow;
import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegularTestResultConverter {
    private final StudentService studentService;
    private final RegularTestService regularTestService;
    private final RegularTestResultService regularTestResultService;

    public RegularTestResultShow convertRegularTestResultToRegularTestResultShow(RegularTestResult regularTestResult){
        RegularTestResultShow regularTestResultShow = new RegularTestResultShow();
        regularTestResultShow.setId(regularTestResult.getId());
        regularTestResultShow.setStudentName(regularTestResult.getStudent().getName());
        regularTestResultShow.setJapanese(regularTestResult.getJapanese());
        regularTestResultShow.setMath(regularTestResult.getMath());
        regularTestResultShow.setEnglish(regularTestResult.getEnglish());
        regularTestResultShow.setScience(regularTestResult.getScience());
        regularTestResultShow.setSocial(regularTestResult.getSocial());
        regularTestResultShow.setPe(regularTestResult.getPe());
        regularTestResultShow.setArt(regularTestResult.getArt());
        regularTestResultShow.setTech(regularTestResult.getTech());
        regularTestResultShow.setMusic(regularTestResult.getMusic());
        return regularTestResultShow;
    }

    public List<RegularTestResultShow> convertRegularTestResultsToRegularTestResultShows(List<RegularTestResult> regularTestResults){
        List<RegularTestResultShow> regularTestResultShows = new ArrayList<>();
        for(RegularTestResult result:regularTestResults){
            RegularTestResultShow regularTestResultShow = convertRegularTestResultToRegularTestResultShow(result);
            regularTestResultShows.add(regularTestResultShow);
        }
        return regularTestResultShows;
    }

    public RegularTestResult convertRegularTestResultShowToRegularTestResult(RegularTestResultShow regularTestResultShow){
        Optional<RegularTestResult> optionalRegularTestResult = regularTestResultService.getRegularTestResultById(regularTestResultShow.getId());
        if(optionalRegularTestResult.isPresent()){
            RegularTestResult regularTestResult = optionalRegularTestResult.get();
            regularTestResult.setJapanese(regularTestResultShow.getJapanese());
            regularTestResult.setMath(regularTestResultShow.getMath());
            regularTestResult.setEnglish(regularTestResultShow.getEnglish());
            regularTestResult.setScience(regularTestResultShow.getScience());
            regularTestResult.setSocial(regularTestResultShow.getSocial());
            regularTestResult.setArt(regularTestResultShow.getArt());
            regularTestResult.setPe(regularTestResultShow.getPe());
            regularTestResult.setTech(regularTestResultShow.getTech());
            regularTestResult.setMusic(regularTestResultShow.getMusic());
            return regularTestResult;
        }else{
            return null;
        }

    }

    public List<RegularTestResult> convertRegularTestResultShowsToRegularTestResults(List<RegularTestResultShow> regularTestResultShows){
        List<RegularTestResult> regularTestResults = new ArrayList<>();
        for(RegularTestResultShow show:regularTestResultShows){
            regularTestResults.add(convertRegularTestResultShowToRegularTestResult(show));
        }

        return regularTestResults;
    }
}

