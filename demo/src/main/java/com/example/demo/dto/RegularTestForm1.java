package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RegularTestForm1 {


    @Min(value = 1, message = "学年は1以上である必要があります")
    @Max(value = 3, message = "学年は3以下である必要があります")
    private Integer grade;

    @NotNull(message="学期を選んでください")
    private Integer semester;

    @NotNull(message = "中間か期末を選択してください")
    private Integer isMid; // 中間か期末：期末は0,中間は１


}


//schoolIdを送るためだけのForm