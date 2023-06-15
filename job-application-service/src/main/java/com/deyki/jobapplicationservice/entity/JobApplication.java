package com.deyki.jobapplicationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_application")
public class JobApplication {

    @Id
    @SequenceGenerator(name = "job_application_sequence", sequenceName = "job_application_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_application_sequence")
    private Long jobApplicationID;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "job_id", nullable = false)
    private Long jobID;

    @Column(name = "it_area", nullable = false)
    private String itArea;

    @Column(name = "tech_stack", nullable = false)
    private List<String> techStack;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "type_of_employment", nullable = false)
    private String typeOfEmployment;

    @Column(name = "remote_interview", nullable = false)
    private Boolean remoteInterview;

    @Column(name = "home_office", nullable = false)
    private Boolean homeOffice;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "salary", nullable = false)
    private Integer salary;
}