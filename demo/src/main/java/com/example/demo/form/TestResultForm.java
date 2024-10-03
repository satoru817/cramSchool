package com.example.demo.form;

import com.example.demo.entity.Student;
import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class TestResultForm {
    //もっとうまくできる気がする(studentNameとstudentIdを両方保存しない方法があるはずだ）が、とりあえず動くものを作る。
    private String studentName;

    private Integer studentId;

    public TestResultForm(Student student){
        this.studentId = student.getId();
        this.studentName = student.getName();
    }

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

}
