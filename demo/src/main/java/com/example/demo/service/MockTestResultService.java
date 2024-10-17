package com.example.demo.service;

import com.example.demo.entity.MockTestResult;
import com.example.demo.repository.MockTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MockTestResultService {
    private final MockTestResultRepository mockTestResultRepository;

    public Page<MockTestResult> getAllMockTestResult(Pageable pageable){
        return mockTestResultRepository.findAll(pageable);
    }
}
