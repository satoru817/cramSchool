package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Entity
@Data
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="code")
    private Long code;

    @Column(name="el1")
    private Integer el1;//小学校に入学した時の西暦が入る。学年判定用。

    @Column(name="name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<SchoolStudent> schoolStudents; // Connection to SchoolStudent

}
