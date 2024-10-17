package com.example.demo.repository;

import com.example.demo.entity.SchoolRecord;
import com.example.demo.entity.SchoolRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchoolRecordRepository extends JpaRepository<SchoolRecord, SchoolRecordId>, PagingAndSortingRepository<SchoolRecord,SchoolRecordId> {
}
