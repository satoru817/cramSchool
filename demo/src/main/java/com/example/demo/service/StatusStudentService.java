package com.example.demo.service;

import com.example.demo.repository.StatusStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusStudentService {
    private final StatusStudentRepository statusStudentRepository;


}
