package com.example.demo.dto;

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

    @NotNull(message="生徒の学年を選択してください。")
    private Integer grade;//小学一年生を1として増やしていく。生徒の学年情報を取得する。

    @NotBlank(message="生徒名を入力してください")
    private String name;

    @NotNull(message="ステータスを選択してください。")
    private Integer statusId;//schoolIdとよく似た実装

    @NotNull(message="生徒の所属する学校を選んでください。無ければ学校を登録して下さい。")
    private Integer schoolId;//学校のIdをstudent_registerからおくってもらう。
}
