package com.example.demo.repository;

import com.example.demo.entity.MockTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockTestResultRepository extends JpaRepository<MockTestResult,Integer> {
}
