package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.Student;
import com.example.demo.repository.RegularTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegularTestResultService {
    private final RegularTestResultRepository regularTestResultRepository;

    public Optional<RegularTestResult> getRegularTestResultByRegularTestAndStudent(RegularTest regularTest,Student student){
        return regularTestResultRepository.findByRegularTestAndStudent(regularTest, student);
    }


    public void save(RegularTestResult regularTestResult) {
        regularTestResultRepository.save(regularTestResult);
    }
}
