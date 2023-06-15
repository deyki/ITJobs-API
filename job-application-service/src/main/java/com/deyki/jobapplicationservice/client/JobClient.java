package com.deyki.jobapplicationservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface JobClient {

    @GetExchange("/api/job/{jobID}")
    ResponseEntity<JobResponseModel> getJobById(@PathVariable Long jobID);
}
