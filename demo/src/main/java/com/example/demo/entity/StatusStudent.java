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

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="changed_at")
    private Date changedAt;
}
