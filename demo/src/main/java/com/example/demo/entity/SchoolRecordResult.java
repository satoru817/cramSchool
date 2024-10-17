package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(SchoolRecordResultId.class)
@Table(name="school_record_result")
public class SchoolRecordResult {

    @Id
    @Column(name="student_id")
    private Integer studentId;

    @ManyToOne
    @JoinColumn(name="student_id",insertable = false,updatable = false)
    private Student student;

    @Id
    @Column(name="school_record_id")
    private Integer schoolRecordId;

    @ManyToOne
    @JoinColumn(name="school_record_id",insertable = false,updatable = false)
    private SchoolRecord schoolRecord;

    @Column(name="japanese")
    private Integer japanese; // 国語の得点

    @Column(name="math")
    private Integer math; // 数学の得点

    @Column(name="english")
    private Integer english; // 英語の得点

    @Column(name="science")
    private Integer science; // 理科の得点

    @Column(name="social")
    private Integer social; // 社会の得点

    @Column(name="music")
    private Integer music; // 音楽の得点

    @Column(name="art")
    private Integer art; // 美術の得点

    @Column(name="technology")
    private Integer technology; // 技術の得点

    @Column(name="pe")
    private Integer pe; // 体育の得点

}
