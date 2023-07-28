# Tech-stack:

* Java-17

* Spring-boot-3.1.0

* Spring-security-6

* Spring-data-jpa

* Spring-cloud-gateway

* Spring-cloud-netflix-eureka

* PostgreSQL

* JUnit5

* Mockito

# Description

* ITJobs-API is based on microservice architecture. It consist of user-service, job-service, job-application-service, discovery-server and api-gateway.

* User service is responsible to provide authentication and authorization, profile details creation and provision of user details.

* Job service is responsible to provide basic CRUD operations like: create new job post, getJob, getAllJobs, delete Job.

* Job application service is responsible to provide job applying mechanism. Users can apply for a job. Users can check whether other users has applied for a job they posted.

* Discovery server is responsible to hold information about every client-service applications. Every micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address.

* Api gateway is responsible to route requests based on some criteria and check if the request has authorization header with valid json web token.
