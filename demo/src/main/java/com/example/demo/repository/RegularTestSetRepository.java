package com.example.demo.repository;

import com.example.demo.entity.RegularTestSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularTestSetRepository extends JpaRepository<RegularTestSet,Integer> {
    public RegularTestSet findByGradeAndTermAndIsMidAndSemester(Integer grade,Integer term,Integer isMid,Integer semester);
}
