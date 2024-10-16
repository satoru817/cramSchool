package com.example.demo.repository;

import com.example.demo.entity.MockTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockTestRepository extends JpaRepository<MockTest,Integer> {
}
