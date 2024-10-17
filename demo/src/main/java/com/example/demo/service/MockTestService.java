package com.example.demo.service;

import com.example.demo.entity.MockTest;
import com.example.demo.repository.MockTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MockTestService {
    private final MockTestRepository mockTestRepository;

    public Page<MockTest> getAllMockTests(Pageable pageable){
        return mockTestRepository.findAll(pageable);
    }

}
