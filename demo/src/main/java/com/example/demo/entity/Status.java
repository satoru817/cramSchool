package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="status")
public class Status {
    @Column(name="status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer statusId;

    @Column(name="name")
    @NotBlank(message="ステータスを入力してください。")
    private String name;
}
