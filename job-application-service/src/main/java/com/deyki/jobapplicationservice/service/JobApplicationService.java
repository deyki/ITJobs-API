package com.deyki.jobapplicationservice.service;

import com.deyki.jobapplicationservice.model.JobApplicationRequest;
import com.deyki.jobapplicationservice.model.JobApplicationResponse;
import com.deyki.jobapplicationservice.model.ResponseModel;

import java.util.List;

public interface JobApplicationService {
    ResponseModel newJobApplication(JobApplicationRequest jobApplicationRequest);

    List<JobApplicationResponse> getJobApplicationByJobId(Long jobID);

    List<JobApplicationResponse> getJobApplicationByUsername(String username);

    void validateUserId(Long userID);

    void validateJobId(Long jobID);
}