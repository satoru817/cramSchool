package com.example.demo.repository;

import com.example.demo.entity.MockTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MockTestRepository extends JpaRepository<MockTest,Integer> , PagingAndSortingRepository<MockTest,Integer> {
}
