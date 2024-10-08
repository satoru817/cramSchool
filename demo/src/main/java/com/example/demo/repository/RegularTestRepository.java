package com.example.demo.repository;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegularTestRepository extends JpaRepository<RegularTest,Integer> {
    public List<RegularTest> findAll();
    public RegularTest getById(Integer id);
    public List<RegularTest> getBySchoolAndGradeAndSemesterAndIsMidOrderByDateDesc(School school,Integer grade, Integer semester,Integer isMid);
}
