package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "mock_test_results")
@Getter
@Setter
public class MockTestResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_test_result_id")
    private Integer mockTestResultId;

    @ManyToOne
    @JoinColumn(name = "mock_test_id", nullable = false)
    private MockTest mockTest;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "japanese")
    private Integer japanese;

    @Column(name = "japanese_ss")
    private Integer japaneseSs;

    @Column(name = "math")
    private Integer math;

    @Column(name = "math_ss")
    private Integer mathSs;

    @Column(name = "english")
    private Integer english;

    @Column(name = "english_ss")
    private Integer englishSs;

    @Column(name = "science")
    private Integer science;

    @Column(name = "science_ss")
    private Integer scienceSs;

    @Column(name = "social")
    private Integer social;

    @Column(name = "social_ss")
    private Integer socialSs;

    @Column(name = "jme_ss") // 3科目偏差値
    private Integer jmeSs;

    @Column(name = "jmess_ss") // 5科目偏差値
    private Integer jmessSs;

    @Column(name = "dream_school1")
    private String dreamSchool1;

    @Column(name = "dream_school1_probability")
    private Integer dreamSchool1Probability;

    @Column(name = "dream_school2")
    private String dreamSchool2;

    @Column(name = "dream_school2_probability")
    private Integer dreamSchool2Probability;

    @Column(name = "dream_school3")
    private String dreamSchool3;

    @Column(name = "dream_school3_probability")
    private Integer dreamSchool3Probability;

    @Column(name = "dream_school4")
    private String dreamSchool4;

    @Column(name = "dream_school4_probability")
    private Integer dreamSchool4Probability;

    @Column(name = "dream_school5")
    private String dreamSchool5;

    @Column(name = "dream_school5_probability")
    private Integer dreamSchool5Probability;

    @Column(name = "dream_school6")
    private String dreamSchool6;

    @Column(name = "dream_school6_probability")
    private Integer dreamSchool6Probability;
}
