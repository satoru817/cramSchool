package com.example.demo.service;

import com.example.demo.entity.StatusStudent;
import com.example.demo.repository.StatusStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusStudentService {
    private final StatusStudentRepository statusStudentRepository;

    public void save(StatusStudent s){
        statusStudentRepository.save(s);
    }

    public List<StatusStudent> getStatusStudentByStudentIdOrdered(Integer studentId){
        return statusStudentRepository.findByStudentIdOrderByCreatedAtAsc(studentId);
    }
}
