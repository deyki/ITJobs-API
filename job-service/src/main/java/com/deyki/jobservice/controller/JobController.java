package com.deyki.jobservice.controller;

import com.deyki.jobservice.model.JobRequestModel;
import com.deyki.jobservice.model.JobResponseModel;
import com.deyki.jobservice.model.ResponseModel;
import com.deyki.jobservice.service.impl.JobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobServiceImpl jobService;

    @PostMapping("/create/{userID}")
    public ResponseEntity<ResponseModel> createJob(@PathVariable Long userID, @RequestBody JobRequestModel jobRequestModel) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.createNewJob(userID, jobRequestModel));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobResponseModel>> getAllJobs() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.getAllJobs());
    }
}
