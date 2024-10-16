package com.example.demo.repository;

import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("select s from Student s " +
            "where s.id != 1")
    public List<Student> getAllWithoutAverageMan();

    Student getByCode(Long code);


    @Override
    public Student getById(Integer id);


    @Query("SELECT s FROM Student s " +
            "JOIN s.schoolStudents k " +
            "WHERE s.el1 = :el1 " +
            "AND k.school.id = :schoolId " +
            "AND :specificDate BETWEEN k.createdAt AND k.changedAt")
    List<Student> findStudentsByEl1AndDateRangeAndSchoolId(
            @Param("el1") Integer el1,
            @Param("specificDate") LocalDate specificDate,
            @Param("schoolId") Integer schoolId);

    Optional<Student> findByEl1AndName(Integer el1, String name);
}
