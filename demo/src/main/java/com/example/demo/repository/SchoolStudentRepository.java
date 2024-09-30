package com.example.demo.repository;

import com.example.demo.entity.SchoolStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolStudentRepository extends JpaRepository<SchoolStudent,Integer> {
}
