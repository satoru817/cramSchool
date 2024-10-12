package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class RegularTestResultShow {
    private Integer id;
    private String studentName;

    private Integer japanese;

    private Integer math;

    private Integer english;

    private Integer science;

    private Integer social;

    private Integer music;

    private Integer art;

    private Integer tech;

    private Integer pe;


}
