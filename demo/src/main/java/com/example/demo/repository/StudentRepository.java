package com.example.demo.repository;

import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {


    Student getByCode(Long code);


    @Override
    public Student getById(Integer id);

    public List<Student> findAllBySchoolAndEl1(School school, Integer el1);
}
