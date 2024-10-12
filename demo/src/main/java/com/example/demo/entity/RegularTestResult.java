package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="regular_test_result")
public class RegularTestResult {
    @Id
    @Column(name="regular_test_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="regular_test_id")
    private RegularTest regularTest;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @Column(name="japanese")
    private Integer japanese;

    @Column(name="math")
    private Integer math;

    @Column(name="english")
    private Integer english;

    @Column(name="science")
    private Integer science;

    @Column(name="social")
    private Integer social;

    @Column(name="music")
    private Integer music;

    @Column(name="art")
    private Integer art;

    @Column(name="tech")
    private Integer tech;

    @Column(name="pe")
    private Integer pe;

}
