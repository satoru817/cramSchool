package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.repository.RegularTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegularTestService {
    private final RegularTestRepository regularTestRepository;
    private final TermAndYearService termAndYearService;

    public RegularTest getBySchoolAndGradeAndSemesterAndIsMidAndTerm(School school, Integer grade, Integer semester, Integer isMid, Integer thisTerm) {
        List<RegularTest> regularTestList = regularTestRepository.getBySchoolAndGradeAndSemesterAndIsMidOrderByDateDesc(school,grade,semester,isMid);
        if(regularTestList.isEmpty()){//nullpointerexcption回避
            return null;
        }else {
            if (TermAndYearService.getTerm(regularTestList.getFirst().getDate()) == termAndYearService.getTerm()) {
                return regularTestList.getFirst();
            } else {
                return null;
            }

        }
    }


    public void save(RegularTest regularTest){
        regularTestRepository.save(regularTest);
    }

    public List<RegularTest> fetchAll(){
        return regularTestRepository.findAll();
    }

    public RegularTest fetchById(Integer id){
        return regularTestRepository.getById(id);
    }
}
