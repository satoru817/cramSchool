package com.example.demo.repository;

import com.example.demo.entity.KlassStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassStudentRepository extends JpaRepository<KlassStudent,Integer> {
}
