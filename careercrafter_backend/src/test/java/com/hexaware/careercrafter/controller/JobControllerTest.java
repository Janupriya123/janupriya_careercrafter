package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobDTO;
import com.hexaware.careercrafter.service.IJobService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class JobControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IJobService service;



    @Test
    void getJobById() {

        JobDTO dto = restTemplate.getForObject(
                "http://localhost:8080/job/1",
                JobDTO.class);

        assertEquals(1, dto.getJobId());
    }

    @Test
    void getAllJobs() {

        ResponseEntity<JobDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/job/all",
                        JobDTO[].class);

        JobDTO[] jobs = response.getBody();

        assertTrue(jobs.length > 0);
    }

    @Test
    void updateJob() {

        JobDTO dto = service.getJobById(2);
        dto.setEmployerId(1);
        dto.setTitle("Senior Java Developer");
        dto.setDescription("Spring Boot Developer");
        dto.setLocation("Chennai");
        dto.setExperienceRequired(2);
        dto.setSalaryMin(500000.0);
        dto.setSalaryMax(1000000.0);
        dto.setJobType("Full Time");
        dto.setVacancies(2);
        dto.setStatus("OPEN");

        restTemplate.put(
                "http://localhost:8080/job/update/2",
                dto);

        JobDTO updated =
                service.getJobById(2);

        assertEquals(
                "Senior Java Developer",
                updated.getTitle());
    }

    @Test
    void getJobsByEmployerId() {

        ResponseEntity<JobDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/job/employer/1",
                        JobDTO[].class);

        JobDTO[] jobs = response.getBody();

        assertTrue(jobs.length>0);
    }

    @Test
    void searchJobsByTitle() {

        ResponseEntity<JobDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/job/search/title/Java Developer",
                        JobDTO[].class);

        JobDTO[] jobs = response.getBody();

        assertTrue(jobs.length > 0);
    }

    @Test
    void searchJobsByLocation() {

        ResponseEntity<JobDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/job/search/location/Chennai",
                        JobDTO[].class);

        JobDTO[] jobs = response.getBody();

        assertTrue(jobs.length > 0);
    }

    @Test
    void searchJobsByJobType() {

        ResponseEntity<JobDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/job/search/type/Full Time",
                        JobDTO[].class);

        JobDTO[] jobs = response.getBody();

        assertTrue(jobs.length > 0);
    }

    @Test
    void searchJobsByExperience() {

        ResponseEntity<JobDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/job/search/experience/2",
                        JobDTO[].class);

        JobDTO[] jobs = response.getBody();

        assertTrue(jobs.length > 0);
    }
    @Test
    @Disabled
    void addJob() {

        JobDTO dto = new JobDTO();

        dto.setEmployerId(1);
        dto.setTitle("JUnit Test Job");
        dto.setDescription("Testing Job Add");
        dto.setLocation("Chennai");
        dto.setExperienceRequired(2);
        dto.setSalaryMin(400000.0);
        dto.setSalaryMax(800000.0);
        dto.setJobType("Full Time");
        dto.setVacancies(2);
        dto.setStatus("OPEN");

        ResponseEntity<JobDTO> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/job/add",
                        dto,
                        JobDTO.class);

        JobDTO job = response.getBody();

        assertEquals(
                "JUnit Test Job",
                job.getTitle());
    }
    @Test
    @Disabled
    void deleteJob() {

        JobDTO dto = new JobDTO();

        dto.setEmployerId(1);
        dto.setTitle("Delete Test Job");
        dto.setDescription("Delete Testing");
        dto.setLocation("Chennai");
        dto.setExperienceRequired(1);
        dto.setSalaryMin(300000.0);
        dto.setSalaryMax(500000.0);
        dto.setJobType("Full Time");
        dto.setVacancies(1);
        dto.setStatus("OPEN");

        JobDTO saved =
                restTemplate.postForObject(
                        "http://localhost:8080/job/add",
                        dto,
                        JobDTO.class);

        int jobId = saved.getJobId();
        restTemplate.delete(
                "http://localhost:8080/job/delete/" + jobId);

        assertThrows(
                Exception.class,
                () -> restTemplate.getForObject(
                        "http://localhost:8080/job/" + jobId,
                        JobDTO.class));
    }
}
