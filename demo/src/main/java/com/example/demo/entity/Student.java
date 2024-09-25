package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
@Table(name="students")
public class Student {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="status")
    @NotBlank(message="生徒のステータスを選んでください。")
    private String status;

    @ManyToOne
    @JoinColumn(name="school_id")
    private School school;


}
