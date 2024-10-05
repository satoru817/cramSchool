package com.example.demo.service;

import com.example.demo.entity.Klass;
import com.example.demo.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassService {
    private final ClassRepository classRepository;
    public List<Klass> getAll(){
        return classRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id){
        classRepository.deleteById(id);
    }

    public void save(Klass klass){
        classRepository.save(klass);
    }
}
