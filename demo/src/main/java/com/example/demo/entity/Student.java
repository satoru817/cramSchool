package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="code")
    private Long code;

    @Column(name="el1")
    private Integer el1;

    @Column(name="name")
    private String name;

    @JsonIgnore // これで循環参照を防ぎます
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<SchoolStudent> schoolStudents;
}
