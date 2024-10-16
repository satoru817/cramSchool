package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="mock_tests")
public class MockTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mock_test_id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="date")
    private LocalDate date;
}
