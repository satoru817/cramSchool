package com.example.demo.service;

import com.example.demo.entity.SchoolStudent;
import com.example.demo.entity.Student;
import com.example.demo.repository.SchoolStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolStudentService {
    private SchoolStudentRepository schoolStudentRepository;

    public void save(SchoolStudent schoolStudent){
        schoolStudentRepository.save(schoolStudent);
    }

    public List<SchoolStudent> getAllSchoolStudentsOrderedByCreatedAt(){
        return schoolStudentRepository.findAllByOrderByCreatedAtAsc();
    }

    public List<SchoolStudent> getSchoolStudentsByStudentIdOrdered(Integer studentId){
        return schoolStudentRepository.findByStudentIdOrderByCreatedAtDsc(studentId);
    }


}
