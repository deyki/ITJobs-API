package com.deyki.jobservice.entity;

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
@Table(name = "job")
public class Job {

    @Id
    @SequenceGenerator(name = "job_sequence", sequenceName = "job_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_sequence")
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

    @Column(name = "app_user", nullable = false)
    private String user;
}