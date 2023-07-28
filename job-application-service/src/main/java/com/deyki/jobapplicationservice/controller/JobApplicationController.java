package com.deyki.jobapplicationservice.controller;

import com.deyki.jobapplicationservice.model.JobApplicationRequest;
import com.deyki.jobapplicationservice.model.JobApplicationResponse;
import com.deyki.jobapplicationservice.model.ResponseModel;
import com.deyki.jobapplicationservice.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-application")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createNewJobApplication(@RequestBody JobApplicationRequest jobApplicationRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.newJobApplication(jobApplicationRequest));
    }

    @GetMapping("/{jobID}")
    public ResponseEntity<List<JobApplicationResponse>> getJobApplicationByJobId(@PathVariable Long jobID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.getJobApplicationByJobId(jobID));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JobApplicationResponse>> getJobApplicationByUsername(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.getJobApplicationByUsername(username));
    }
}