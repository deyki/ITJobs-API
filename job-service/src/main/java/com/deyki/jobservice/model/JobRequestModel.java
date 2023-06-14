package com.deyki.jobservice.model;

import com.deyki.jobservice.client.UserResponse;
import lombok.Data;

import java.util.List;

@Data
public class JobRequestModel {
    private String itArea;
    private List<String> techStack;
    private String location;
    private String typeOfEmployment;
    private Boolean remoteInterview;
    private Boolean homeOffice;
    private String level;
    private Integer experience;
    private Integer salary;
}
