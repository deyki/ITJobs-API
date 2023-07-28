package com.deyki.jobservice.controller;

import com.deyki.jobservice.model.JobRequestModel;
import com.deyki.jobservice.model.JobResponseModel;
import com.deyki.jobservice.model.ResponseModel;
import com.deyki.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

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

    @GetMapping("/{jobID}")
    public ResponseEntity<JobResponseModel> getJobById(@PathVariable Long jobID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.getJobById(jobID));
    }

    @GetMapping("/homeOffice")
    public ResponseEntity<List<JobResponseModel>> getJobsWithHomeOffice() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.getJobsWithHomeOffice());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JobResponseModel>> getJobsByUsername(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.getJobsByUsername(username));
    }

    @PutMapping("/update/active/{jobID}/{userID}")
    public ResponseEntity<ResponseModel> updateJobActiveByUserIdAndJobId(@PathVariable Long jobID, @PathVariable Long userID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.updateJobActiveByUserIdAndJobId(jobID, userID));
    }

    @DeleteMapping("/delete/{jobID}")
    public ResponseEntity<ResponseModel> deleteJobById(@PathVariable Long jobID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.deleteJobById(jobID));
    }
}