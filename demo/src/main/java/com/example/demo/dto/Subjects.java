package com.example.demo.dto;

import lombok.Data;

@Data
public class Subjects {
    private String japanese;
    private String math;
    private String english;
    private String science;
    private String social;
    private String music;
    private String art;
    private String tech;
    private String pe;
    public Subjects(){
        this.japanese="japanese";
        this.math="math";
        this.english="english";
        this.science="science";
        this.social="social";
        this.music="music";
        this.art="art";
        this.tech="tech";
        this.pe="pe";
    }
}
