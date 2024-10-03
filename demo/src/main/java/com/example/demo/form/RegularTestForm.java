package com.example.demo.form;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class RegularTestForm {
    // idが無い、Schoolの情報をIdで保持することに注意
    @NotNull(message = "学校を選択してください")
    private Integer schoolId;

    @NotNull(message = "実施日を選択してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")//date picker利用の為にこうしている
    private Date date;

    @Min(value = 1, message = "学年は1以上である必要があります")
    @Max(value = 3, message = "学年は3以下である必要があります")
    private Integer grade;

    @NotNull(message="学期を選んでください")
    private Integer semester;

    @NotNull(message = "中間か期末を選択してください")
    private Integer isMid; // 中間か期末：期末は0,中間は１

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