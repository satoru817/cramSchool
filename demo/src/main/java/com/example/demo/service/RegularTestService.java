package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import com.example.demo.repository.RegularTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegularTestService {
    private final RegularTestRepository regularTestRepository;
    private final TermAndYearService termAndYearService;

    public Optional<RegularTest> getBySchoolAndGradeAndSemesterAndIsMidAndTerm(School school, Integer grade, Integer semester, Integer isMid, Integer thisTerm) {
        List<RegularTest> regularTestList = regularTestRepository.getBySchoolAndGradeAndSemesterAndIsMidOrderByDateDesc(school,grade,semester,isMid);
        if(regularTestList.isEmpty()){//nullpointerexcption回避
            return null;//これがダメなのかな
        }else {
            if (TermAndYearService.getTerm(regularTestList.getFirst().getDate()).equals(termAndYearService.getTerm())) {
                return Optional.ofNullable(regularTestList.getFirst());
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
