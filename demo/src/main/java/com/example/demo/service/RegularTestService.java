package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.repository.RegularTestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegularTestService {
    private RegularTestRepository regularTestRepository;
    public RegularTestService (RegularTestRepository regularTestRepository){
        this.regularTestRepository = regularTestRepository;
    }

    public void save(RegularTest regularTest){
        regularTestRepository.save(regularTest);
    }

    public List<RegularTest> fetchAll(){
        return regularTestRepository.findAll();
    }

    public RegularTest fetchById(Integer id){
        return regularTestRepository.getById(id);
    }
}
