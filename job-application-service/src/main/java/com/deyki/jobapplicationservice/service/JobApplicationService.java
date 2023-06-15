package com.deyki.jobapplicationservice.service;

import com.deyki.jobapplicationservice.model.JobApplicationRequest;
import com.deyki.jobapplicationservice.model.ResponseModel;

public interface JobApplicationService {

    ResponseModel newJobApplication(JobApplicationRequest jobApplicationRequest);

    void validateUserId(Long userID);

    void validateJobId(Long jobID);
}