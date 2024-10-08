package com.example.demo.repository;

import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClassStudentRepository extends JpaRepository<KlassStudent,Integer> {
    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = '国語' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForJapaneseByStudentAndDate(@Param("student") Student student, @Param("date") Date date);


    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = '数学' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForMathByStudentAndDate(@Param("student") Student student, @Param("date") Date date);

    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = '理科' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForScienceByStudentAndDate(@Param("student") Student student, @Param("date") Date date);

    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = '英語' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForEnglishByStudentAndDate(@Param("student") Student student, @Param("date") Date date);


    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = '社会' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForSocialByStudentAndDate(@Param("student") Student student, @Param("date") Date date);

    @Query(value = "SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = :subject " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassStudentForASubjectAndDate(@Param("student")Student student, @Param("date")Date date , @Param("subject")String subject);


}
