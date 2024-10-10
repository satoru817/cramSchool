package com.example.demo.repository;

import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.School;
import com.example.demo.entity.SchoolStudent;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SchoolStudentRepository extends JpaRepository<SchoolStudent,Integer> {
   List<SchoolStudent> findAllByOrderByCreatedAtAsc();

   List<SchoolStudent> findByStudentIdOrderByCreatedAtAsc(Integer studentId);

   @Query("SELECT k FROM SchoolStudent k " +
           "JOIN k.student s " +
           "WHERE s.el1 = :el1 " +
           "AND k.school = :school " +
           "AND :date BETWEEN k.createdAt AND k.changedAt " )
   List<SchoolStudent> findSchoolStudentBySchoolAndDateAndEl1(@Param("school") School school, @Param("el1") Integer el1, @Param("date") java.sql.Date date);

}

