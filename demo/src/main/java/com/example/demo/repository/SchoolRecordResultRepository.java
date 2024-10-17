package com.example.demo.repository;

import com.example.demo.entity.SchoolRecord;
import com.example.demo.entity.SchoolRecordResult;
import com.example.demo.entity.SchoolRecordResultId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRecordResultRepository extends JpaRepository<SchoolRecordResult, SchoolRecordResultId> {
}
