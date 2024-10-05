package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name="status_students")
public class StatusStudent {
    @Id
    @Column(name="status_student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusStudentId;

    @Column(name="status_id")
    private Integer statusId;

    @Column(name="student_id")
    private Integer studentId;

    @Column(name="created_at")
    private Date cratedAt;

    @Column(name="changed_at")
    private Date changedAt;
}
