package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name", unique = true)
    @NotBlank(message="学校名を入力してください")
    private String name;

    @OneToMany(mappedBy = "school")
    private List<SchoolStudent> schoolStudents; // Connection to SchoolStudent
}
