package com.deyki.jobapplicationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class JobApplicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobApplicationServiceApplication.class, args);
	}
}
