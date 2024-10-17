package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(SchoolRecordId.class)
@Table(name="school_record")
public class SchoolRecord {

    @Id
    @Column(name="school_id",nullable = false)
    private Integer schoolId;

    @Id
    @Column(name="term",nullable = false)
    private Integer term;

    @Id
    @Column(name="grade",nullable = false)
    private Integer grade;

    @Id
    @Column(name="semester",nullable = false)
    private Integer semester;

    @ManyToOne
    @JoinColumn(name="school_id",insertable = false,updatable = false)
    private School school;

}
