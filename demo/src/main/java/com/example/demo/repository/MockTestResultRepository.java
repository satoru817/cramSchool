package com.example.demo.repository;

import com.example.demo.entity.MockTest;
import com.example.demo.entity.MockTestResult;
import com.example.demo.entity.MockTestResultId; // 複合主キーのインポート
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MockTestResultRepository extends JpaRepository<MockTestResult, MockTestResultId>, PagingAndSortingRepository<MockTestResult,MockTestResultId> {
}
