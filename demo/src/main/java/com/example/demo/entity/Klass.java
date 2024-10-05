package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="classes")
public class Klass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="class_id")
    private Integer classId;

    @NotBlank(message="科目を選択してください")
    @Column(name="subject")
    private String subject;

    @NotBlank(message="クラス名を入力してください")
    @Column(name="name")
    private String name;
}
