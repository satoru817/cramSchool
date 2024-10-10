package com.example.demo.repository;

import com.example.demo.entity.RegularTestSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegularTestSetRepository extends JpaRepository<RegularTestSet,Integer> {
    public Optional<RegularTestSet> findByGradeAndTermAndIsMidAndSemester(Integer grade, Integer term, Integer isMid, Integer semester);
}
