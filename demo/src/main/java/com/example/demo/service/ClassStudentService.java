package com.example.demo.service;

import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.Student;
import com.example.demo.repository.ClassStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassStudentService {
    private final ClassStudentRepository classStudentRepository;

    private Klass findKlassBySubjectAndStudentAndDate(String subject, Student student, Date date) {
        List<KlassStudent> classStudents;

        switch (subject.toLowerCase()) {
            case "japanese":
                classStudents = classStudentRepository.findKlassForJapaneseByStudentAndDate(student, date);
                break;
            case "math":
                classStudents = classStudentRepository.findKlassForMathByStudentAndDate(student, date);
                break;
            case "english":
                classStudents = classStudentRepository.findKlassForEnglishByStudentAndDate(student, date);
                break;
            case "science":
                classStudents = classStudentRepository.findKlassForScienceByStudentAndDate(student, date);
                break;
            case "social":
                classStudents = classStudentRepository.findKlassForSocialByStudentAndDate(student, date);
                break;
            default:
                return null; // サポートされていない科目の場合
        }

        return (classStudents != null && !classStudents.isEmpty()) ? classStudents.get(0).getKlass() : null;
    }

    public Klass findKlassForJapaneseByStudentAndDate(Student student, Date date) {
        return findKlassBySubjectAndStudentAndDate("japanese", student, date);
    }

    public Klass findKlassForMathByStudentAndDate(Student student, Date date) {
        return findKlassBySubjectAndStudentAndDate("math", student, date);
    }

    public Klass findKlassForEnglishByStudentAndDate(Student student, Date date) {
        return findKlassBySubjectAndStudentAndDate("english", student, date);
    }

    public Klass findKlassForScienceByStudentAndDate(Student student, Date date) {
        return findKlassBySubjectAndStudentAndDate("science", student, date);
    }

    public Klass findKlassForSocialByStudentAndDate(Student student, Date date) {
        return findKlassBySubjectAndStudentAndDate("social", student, date);
    }

    public boolean isBelongToAClassOfASubject(Student student, Date date, String subject) {
        List<KlassStudent> classStudents = classStudentRepository.findKlassStudentForASubjectAndDate(student, date, subject);
        return classStudents != null && !classStudents.isEmpty();
    }
}


