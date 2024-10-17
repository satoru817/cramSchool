package com.example.demo.controller;

import com.example.demo.service.SchoolRecordResultService;
import com.example.demo.service.SchoolRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class SchoolRecordController {
    private final SchoolRecordService schoolRecordService;
    private final SchoolRecordResultService schoolRecordResultService;
    //SchoolRecordを作成する
}
