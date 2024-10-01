package com.example.demo.service;

import com.example.demo.repository.RegularTestRepository;
import org.springframework.stereotype.Service;

@Service
public class RegularTestService {
    private RegularTestRepository regularTestRepository;
    public RegularTestService (RegularTestRepository regularTestRepository){
        this.regularTestRepository = regularTestRepository;
    }
}
