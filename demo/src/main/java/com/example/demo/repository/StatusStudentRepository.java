package com.example.demo.repository;

import com.example.demo.entity.StatusStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusStudentRepository extends JpaRepository<StatusStudent, Integer> {

    List<StatusStudent> findByStudentIdOrderByCreatedAtAsc(Integer studentId);
}
