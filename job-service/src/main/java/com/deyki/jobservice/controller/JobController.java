package com.deyki.jobservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @GetMapping("/authenticated")
    public String main() {
        return "Authenticated!";
    }
}
