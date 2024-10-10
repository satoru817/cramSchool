package com.example.demo.service;

import com.example.demo.entity.Student;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
public class TermAndYearService {
    public final LocalDate maxLocalDate = LocalDate.MAX;
    public final LocalDate minLocalDate = LocalDate.MIN;

    public final java.sql.Date maxSqlDate = java.sql.Date.valueOf("9999-12-31");
    public final java.sql.Date minSqlDate = java.sql.Date.valueOf("1970-01-01");

    public java.sql.Date getSqlToday(){
        return  new java.sql.Date(System.currentTimeMillis());

    }
    //method to get today's date dynamically
    public LocalDate getToday(){
        return LocalDate.now();
    }

    public Date getTodayAsDate(){
        return new Date();
    }

    public LocalDate getTodayAsLocalDate(){
        return LocalDate.now();
    }

    public LocalDate getThisYearApril1(){
        return LocalDate.now().withMonth(4).withDayOfMonth(1);
    }

    public int getCurrentYear(){
        return this.getToday().getYear();
    }

    //Studentのel1から学年(1から12)を取得する
    public Integer getSchoolGrade(Student student){

        LocalDate thisYearApril1 = this.getThisYearApril1();


        LocalDate today = this.getToday();

        int currentYear = this.getCurrentYear();

        int grade;

        if(today.isBefore(thisYearApril1)){
            grade = currentYear - student.getEl1();
        }else{
            grade = currentYear - student.getEl1() +1;
        }

        return grade;
    }

    //小学一年生は1高校3年生は12の数値から小学校入学時(3/31)時点の西暦を算出する関数
    public int getWhenEnteredElementarySchool(int abGrade){

        // 今年の4月1日を取得
        LocalDate thisYearApril1 = this.getThisYearApril1();

        // 今日の日付を取得
        LocalDate today = this.getToday();

        //現在の年を取得
        int currentYear = this.getCurrentYear();

        if(today.isBefore(thisYearApril1)){
            return currentYear - abGrade;
        }else{
            return currentYear + 1 - abGrade;
        }
    }

    public int getWhenEnteredElementarySchoolForJuniorHighSchoolStudent(int jhGrade){
        return getWhenEnteredElementarySchool(jhGrade+6);
    }

    //現在の年度を取得する関数
    public Integer getTerm(){
        // 今年の4月1日を取得
        LocalDate thisYearApril1 = this.getThisYearApril1();

        // 今日の日付を取得
        LocalDate today = this.getToday();

        int currentYear = this.getCurrentYear();

        if(today.isBefore(thisYearApril1)){
            return currentYear -1;
        }else{
            return currentYear;
        }

    }

    public static Integer getTerm(java.sql.Date sqlDate) {
        // Calendarインスタンスを作成
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sqlDate);

        // 年と月を取得
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 0から11までの値

        // 4月より前なら前年の年度を返す
        if (month < Calendar.APRIL) {
            return year - 1;
        } else {
            return year; // それ以外は現在の年度を返す
        }
    }


}
