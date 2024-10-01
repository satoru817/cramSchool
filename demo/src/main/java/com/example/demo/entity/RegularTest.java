package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="regular_exam")
public class RegularTest {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="school_id")
    private School school;

    @Column(name="year")
    private Integer year;

    @Column(name="semester")
    private Integer semester;

    @Column(name="is_mid")
    private Integer isMid;

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
