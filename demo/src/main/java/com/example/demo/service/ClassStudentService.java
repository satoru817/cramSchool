package com.example.demo.service;

import com.example.demo.entity.Klass;
import com.example.demo.entity.Student;
import com.example.demo.repository.ClassStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ClassStudentService {
    private final ClassStudentRepository classStudentRepository;
    public Klass findKlassForJapaneseByStudentAndDate(Student student, Date date){
        return classStudentRepository.findKlassForJapaneseByStudentAndDate(student, date).getKlass();
    }

    public Klass findKlassForMathByStudentAndDate(Student student, Date date){
        return classStudentRepository.findKlassForMathByStudentAndDate(student, date).getKlass();
    }

    public Klass findKlassForEnglishByStudentAndDate(Student student, Date date){
        return classStudentRepository.findKlassForEnglishByStudentAndDate(student, date).getKlass();
    }

    public Klass findKlassForScienceByStudentAndDate(Student student, Date date){
        return classStudentRepository.findKlassForScienceByStudentAndDate(student, date).getKlass();
    }

    public Klass findKlassForSocialByStudentAndDate(Student student, Date date){
        return classStudentRepository.findKlassForSocialByStudentAndDate(student, date).getKlass();
    }

    public boolean isBelongToAClassOfASubject(Student student, Date date , String subject){
        return classStudentRepository.findKlassStudentForASubjectAndDate(student, date, subject)!=null;
    }

}
