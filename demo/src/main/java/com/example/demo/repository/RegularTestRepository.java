package com.example.demo.repository;

import com.example.demo.entity.RegularTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularTestRepository extends JpaRepository<RegularTest,Integer> {
}
