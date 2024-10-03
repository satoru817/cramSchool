package com.example.demo.repository;

import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {


    Student getByCode(Long code);


    @Override
    public Student getById(Integer id);


    @Query("SELECT s FROM Student s " +
            "JOIN s.schoolStudents ss " +
            "WHERE s.el1 = :el1 " +
            "AND ss.school.id = :schoolId " +
            "AND :specificDate BETWEEN ss.createdAt AND ss.changedAt")
    List<Student> findStudentsByEl1AndDateRangeAndSchoolId(
            @Param("el1") Integer el1,
            @Param("specificDate") LocalDate specificDate,
            @Param("schoolId") Integer schoolId);
}
