package com.deyki.jobservice.service;

import com.deyki.jobservice.model.JobRequestModel;
import com.deyki.jobservice.model.JobResponseModel;
import com.deyki.jobservice.model.ResponseModel;

import java.util.List;

public interface JobService {

    ResponseModel createNewJob(Long userID, JobRequestModel jobRequestModel);

    List<JobResponseModel> getAllJobs();

    void validateUserId(Long userID);
}