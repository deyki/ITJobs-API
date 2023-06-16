package com.deyki.jobservice.model;

import lombok.Data;

import java.util.List;

@Data
public class JobResponseModel {
    private String itArea;
    private List<String> techStack;
    private String location;
    private String typeOfEmployment;
    private Boolean remoteInterview;
    private Boolean homeOffice;
    private String level;
    private Integer experience;
    private Integer salary;
    private String user;
    private Boolean active;
}