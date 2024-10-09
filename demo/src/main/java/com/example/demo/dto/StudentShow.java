package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentShow {
    private Integer id;

    private String name;

    private Long code;

    private String grade;//文字列として学年を保持する

    private String statusName;

    private String schoolName;
}
