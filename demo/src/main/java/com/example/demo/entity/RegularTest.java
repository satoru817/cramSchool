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
    private Integer id;

    @ManyToOne
    @JoinColumn(name="school_id")
    private School school;

    @Column(name="date")//実施日を保存する理由は転校を把握するため。正確な値でなくても構わない。
    private Date date;

    @Column(name="grade")//学年
    private Integer grade;

    @Column(name="semester")//学期
    private Integer semester;

    @Column(name="is_mid")//中間か期末か判定期末は0,中間は１
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

    public void RegularTest100() {
        this.japanese = 100;
        this.math = 100;
        this.english = 100;
        this.science = 100;
        this.social = 100;
        this.music = 100;
        this.art = 100;
        this.tech = 100;
        this.pe = 100;
    }
}
