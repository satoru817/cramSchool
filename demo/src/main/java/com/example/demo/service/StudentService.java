package com.example.demo.service;

import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public void save(Student student){
        studentRepository.save(student);
    }

    public List<Student> fetchAll(){
        return studentRepository.findAll();
    }

    public Student getById(Integer id){return studentRepository.getById(id);}

    public Student findByCode(Long code){
        return studentRepository.getByCode(code);
    }


    public List<Student> getStudentsByEl1AndDateRangeAndSchoolId(Integer el1, LocalDate date, Integer schoolId) {
        return studentRepository.findStudentsByEl1AndDateRangeAndSchoolId(el1, date, schoolId);
    }
}
