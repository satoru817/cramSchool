package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SchoolRecordResultId implements Serializable {
    private Integer studentId;
    private Integer schoolRecordId;
}
