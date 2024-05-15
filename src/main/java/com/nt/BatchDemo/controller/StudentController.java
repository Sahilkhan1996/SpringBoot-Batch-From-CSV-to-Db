package com.nt.BatchDemo.controller;

import com.nt.BatchDemo.runner.CsvToDbBatchProcessingRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @Autowired
    CsvToDbBatchProcessingRunner csv;

    @GetMapping("/hel")
    public String sayHello() {
        return "Hello!!!";
    }

    @GetMapping("/stu")
    public void callJob() {
        try {
            csv.executeJob();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
