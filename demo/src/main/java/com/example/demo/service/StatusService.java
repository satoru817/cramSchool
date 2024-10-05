package com.example.demo.service;

import com.example.demo.entity.Status;
import com.example.demo.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> findAll(){
        return statusRepository.findAll();
    }

    public void save(Status status){
        statusRepository.save(status);
    }

    @Transactional
    public void deleteStatus(Integer id){
        statusRepository.deleteByStatusId(id);
    }

    public Status getStatusById(Integer id){
        return statusRepository.getReferenceById(id);
    };

}
