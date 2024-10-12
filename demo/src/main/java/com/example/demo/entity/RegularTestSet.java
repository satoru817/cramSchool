package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="regular_test_set")
public class RegularTestSet {
    @Column(name="regular_test_set_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="term")
    private Integer term;

    @Column(name="grade")
    private Integer grade;

    @Column(name="semester")
    private Integer semester;

    @Column(name="is_mid")
    private Integer isMid;
}
