package com.example.demo.repository;

import com.example.demo.entity.SchoolStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolStudentRepository extends JpaRepository<SchoolStudent,Integer> {
   List<SchoolStudent> findAllByOrderByCreatedAtAsc();

   List<SchoolStudent> findByStudentIdOrderByCreatedAtAsc(Integer studentId);
}
