package com.example.demo.repository;

import com.example.demo.entity.RegularTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegularTestRepository extends JpaRepository<RegularTest,Integer> {
    public List<RegularTest> findAll();
    public RegularTest getById(Integer id);

}
