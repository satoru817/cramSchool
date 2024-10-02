package com.example.demo.form;

import com.example.demo.entity.School;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class RegularTestForm {
   //idが無い、Schoolの情報をIdで保持することに注意
    private Integer schoolId;

    private Integer term;

    private Integer grade;

    private Integer semester;


    private Integer isMid;


    private Integer japanese;


    private Integer math;


    private Integer english;


    private Integer science;


    private Integer social;


    private Integer music;


    private Integer art;


    private Integer tech;


    private Integer pe;

    public void RegularTestForm100() {
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

//schoolIdを送るためだけのForm