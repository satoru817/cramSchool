package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="school_id")
    private Integer id;

    @Column(name="name", unique = true)
    @NotBlank(message="学校名を入力してください")
    private String name;

    @JsonIgnore // これで循環参照を防ぎます
    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY) // 遅延ロードも考慮
    private List<SchoolStudent> schoolStudents;
}
