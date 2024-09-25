package com.example.demo.form;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class StudentForm {
    @NotBlank(message="生徒番号を入力してください。")//生徒番号のバリデーションはもっと厳しくしないといけない
    private Integer id;


    @NotBlank(message="生徒名を入力してください")
    private String name;

    @NotBlank(message="生徒のステータスを選んでください。")
    private String status;

    @NotBlank(message="生徒の所属する学校を選んでください。無ければ学校を登録して下さい。")
    private Integer schoolId;//学校のIdをstudent_registerからおくってもらう。
}
