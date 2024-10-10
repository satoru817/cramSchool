package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RegularTestForm {
    // idが無い、Schoolの情報をIdで保持することに注意

    @NotNull(message = "実施日を選択してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")//date picker利用の為にこうしている
    private Date date;

    //これ以下の属性は満点がいくらなのかを運ぶ為にある。
    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer japanese;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer math;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer english;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer science;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer social;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer music;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer art;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer tech;

    @Min(value = 0, message = "得点は0以上である必要があります")
    private Integer pe;

    public void initializeWithPerfectScores() {
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