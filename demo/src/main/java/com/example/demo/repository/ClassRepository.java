package com.example.demo.repository;

import com.example.demo.entity.Klass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Klass,Integer> {
    public List<Klass> findAll();
}
