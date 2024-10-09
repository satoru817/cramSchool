package com.example.demo.repository;

import com.example.demo.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Integer> {
    public void deleteByStatusId(Integer statusId);
    public Status findByName(String name);
}
