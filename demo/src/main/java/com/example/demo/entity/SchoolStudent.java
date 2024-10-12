package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name="school_student")
public class SchoolStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="school_student_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // 遅延ロードを使うことでパフォーマンス向上
    @JoinColumn(name="school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private Student student;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="changed_at")
    private Date changedAt;
}
