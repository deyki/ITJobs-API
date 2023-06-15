package com.deyki.jobapplicationservice.model;

import lombok.Data;

import java.util.List;

@Data
public class JobApplicationResponse {
    private Long jobApplicationID;
    private String username;
    private Long jobID;
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
