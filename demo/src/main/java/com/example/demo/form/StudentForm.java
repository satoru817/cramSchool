package com.example.demo.form;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class StudentForm {
    public StudentForm(){

    }

//    @NotNull(message="生徒番号を入力してください。")//生徒番号のバリデーションはもっと厳しくしないといけない
//    private Integer id;IDは自動生成

    @NotNull(message="生徒コードを入力してください。")
    private Long code;


    @NotBlank(message="生徒名を入力してください")
    private String name;

    @NotBlank(message="生徒のステータスを選んでください。")
    private String status;

    @NotNull(message="生徒の所属する学校を選んでください。無ければ学校を登録して下さい。")
    private Integer schoolId;//学校のIdをstudent_registerからおくってもらう。
}
