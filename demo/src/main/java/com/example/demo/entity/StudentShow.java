package com.example.demo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class StudentShow {
    private Integer id;

    private String name;

    private String status;

    private String schoolName;
}
