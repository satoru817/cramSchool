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

   @Query(value = "SELECT  s.student_id " +
           "FROM school_student k " +
           "JOIN students s ON k.student_id = s.student_id " +
           "JOIN schools sl ON k.school_id = sl.school_id " +
           "WHERE s.el1 = :el1 " +
           "AND sl.school_id = :schoolId " +
           "AND :date BETWEEN k.created_at AND k.changed_at",
           nativeQuery = true)
   List<Integer> findSchoolStudentBySchoolAndDateAndEl1(@Param("schoolId") Integer schoolId, @Param("el1") Integer el1, @Param("date") java.sql.Date date);


}

