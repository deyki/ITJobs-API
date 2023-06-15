package com.deyki.jobapplicationservice.service.impl;

import com.deyki.jobapplicationservice.client.JobClient;
import com.deyki.jobapplicationservice.client.JobResponseModel;
import com.deyki.jobapplicationservice.client.UserClient;
import com.deyki.jobapplicationservice.client.UserResponse;
import com.deyki.jobapplicationservice.entity.JobApplication;
import com.deyki.jobapplicationservice.error.JobNotFoundException;
import com.deyki.jobapplicationservice.error.UserNotFoundException;
import com.deyki.jobapplicationservice.model.JobApplicationRequest;
import com.deyki.jobapplicationservice.model.ResponseModel;
import com.deyki.jobapplicationservice.repository.JobApplicationRepository;
import com.deyki.jobapplicationservice.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository repository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;
    private final JobClient jobClient;

    @Override
    public ResponseModel newJobApplication(JobApplicationRequest jobApplicationRequest) {
        validateUserId(jobApplicationRequest.userID());
        validateJobId(jobApplicationRequest.jobID());

        log.info("userID && jobID are valid!");

        ResponseEntity<UserResponse> userResponse = userClient.getUserById(jobApplicationRequest.userID());
        ResponseEntity<JobResponseModel> jobResponseModel = jobClient.getJobById(jobApplicationRequest.jobID());

        JobApplication jobApplication = modelMapper.map(jobResponseModel.getBody(), JobApplication.class);
        jobApplication.setUserID(jobApplicationRequest.userID());
        jobApplication.setJobID(jobApplicationRequest.jobID());
        jobApplication.setUsername(userResponse.getBody().username());

        repository.save(jobApplication);
        log.info("New job application is added!");

        return new ResponseModel("Application submitted!");
    }

    @Override
    public void validateUserId(Long userID) {
        try {
            userClient.getUserById(userID);
        } catch (RuntimeException exception) {
            throw new UserNotFoundException("User not found!");
        }
    }

    @Override
    public void validateJobId(Long jobID) {
        try {
            jobClient.getJobById(jobID);
        } catch (RuntimeException exception) {
            throw new JobNotFoundException("Job not found!");
        }
    }
}
