package com.example.demo.service;

import com.example.demo.repository.ClassStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassStudentService {
    private final ClassStudentRepository classStudentRepository;
}
