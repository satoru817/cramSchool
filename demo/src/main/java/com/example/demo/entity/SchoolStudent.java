package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="school_student")
public class SchoolStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @JoinColumn(name="school_id")
    private School school;

    @JoinColumn(name="student_id")
    private Student student;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="changed_at")
    private Date changedAt;
}
