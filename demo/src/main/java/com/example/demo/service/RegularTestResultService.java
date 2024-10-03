package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.Student;
import com.example.demo.repository.RegularTestResultRepository;
import org.springframework.stereotype.Service;

@Service
public class RegularTestResultService {
    private RegularTestResultRepository regularTestResultRepository;

    public RegularTestResultService(RegularTestResultRepository regularTestResultRepository){
        this.regularTestResultRepository = regularTestResultRepository;
    }


}
