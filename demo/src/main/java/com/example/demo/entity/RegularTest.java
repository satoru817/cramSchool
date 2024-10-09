package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name="regular_exam")
public class RegularTest {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularTestId;

    @ManyToOne
    @JoinColumn(name="school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name="regular_test_set_id")//いくつかのテストを行った学年、年度、セメスター、中間か期末かでまとめるためのid
    private RegularTestSet regularTestSet;

    @Column(name="date")//実施日を保存する理由は転校を把握するため。正確な値でなくても構わない。
    private Date date;

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
