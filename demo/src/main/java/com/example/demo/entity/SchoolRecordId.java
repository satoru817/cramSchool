package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SchoolRecordId implements Serializable {
    private Integer schoolId;
    private Integer term;
    private Integer grade;
    private Integer semester;
}
