package com.deyki.jobservice.repository;

import com.deyki.jobservice.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j WHERE j.homeOffice = true")
    List<Job> findJobsWithHomeOffice();
    @Query("SELECT j FROM Job j WHERE j.user = ?1")
    List<Job> findJobsByUsername(@Param("user") String user);
}