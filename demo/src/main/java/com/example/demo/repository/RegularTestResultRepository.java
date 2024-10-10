package com.example.demo.repository;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegularTestResultRepository extends JpaRepository<RegularTestResult,Integer> {
    public Optional<RegularTestResult> findByRegularTestAndStudent(RegularTest regularTest, Student student);
}
