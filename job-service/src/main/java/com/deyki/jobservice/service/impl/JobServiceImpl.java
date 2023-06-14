package com.deyki.jobservice.service.impl;

import com.deyki.jobservice.client.UserClient;
import com.deyki.jobservice.client.UserResponse;
import com.deyki.jobservice.entity.Job;
import com.deyki.jobservice.error.UserNotFoundException;
import com.deyki.jobservice.model.JobRequestModel;
import com.deyki.jobservice.model.JobResponseModel;
import com.deyki.jobservice.model.ResponseModel;
import com.deyki.jobservice.repository.JobRepository;
import com.deyki.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserClient userClient;
    private final ModelMapper modelMapper;

    @Override
    public ResponseModel createNewJob(Long userID, JobRequestModel jobRequestModel) {
        validateUserId(userID);

        ResponseEntity<UserResponse> userResponse = userClient.getUserById(userID);

        Job job = modelMapper.map(jobRequestModel, Job.class);
        job.setUser(userResponse.getBody().username());
        jobRepository.save(job);
        log.info("Job created!");

        return new ResponseModel("Job created!");
    }

    @Override
    public List<JobResponseModel> getAllJobs() {
        return jobRepository
                .findAll()
                .stream()
                .map(job -> modelMapper.map(job, JobResponseModel.class))
                .collect(Collectors.toList());
    }

    public void validateUserId(Long userID) {
        try {
            userClient.getUserById(userID);
        } catch (RuntimeException exception) {
            throw new UserNotFoundException("User not found!");
        }
    }
}