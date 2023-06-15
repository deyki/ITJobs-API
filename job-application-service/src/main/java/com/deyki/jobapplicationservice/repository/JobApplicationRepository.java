package com.deyki.jobapplicationservice.repository;

import com.deyki.jobapplicationservice.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    @Query("SELECT j FROM JobApplication j WHERE j.jobID = ?1")
    Optional<JobApplication> findByJobId(@Param("jobID") Long jobID);
}
