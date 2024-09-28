package com.example.demo.service;

import com.example.demo.entity.School;
import com.example.demo.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    private SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }

    public void save(School school){
        schoolRepository.save(school);
    }

    public List<School> fetchAll(){
        return schoolRepository.findAll();
    }

    public School fetchById(Integer id){
        return schoolRepository.getSchoolById(id);
    }

    public School findByName(String schoolName) {
        return schoolRepository.getSchoolByName(schoolName);
    }

    public void deleteById(Integer id) {
        schoolRepository.deleteById(id);
    }
}
