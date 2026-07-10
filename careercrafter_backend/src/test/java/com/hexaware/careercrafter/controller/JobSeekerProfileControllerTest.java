package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobSeekerProfileDTO;
import com.hexaware.careercrafter.service.IJobSeekerProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobSeekerProfileControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IJobSeekerProfileService service;

    @Test
    void getProfileById() {

        JobSeekerProfileDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/profile/1",
                        JobSeekerProfileDTO.class);

        assertEquals(1, dto.getProfileId());
    }

    @Test
    void getAllProfiles() {

        ResponseEntity<JobSeekerProfileDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/profile/all",
                        JobSeekerProfileDTO[].class);

        assertTrue(
                response.getBody().length > 0);
    }

    @Test
    void getProfileByUserId() {

        JobSeekerProfileDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/profile/user/102",
                        JobSeekerProfileDTO.class);

        assertEquals(102, dto.getUserId());
    }

    @Test
    void searchBySkills() {

        ResponseEntity<JobSeekerProfileDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/profile/skills/Java",
                        JobSeekerProfileDTO[].class);

        assertTrue(
                response.getBody().length > 0);
    }
    @Test
    void updateProfile() {

        JobSeekerProfileDTO dto =
                service.getProfileById(1);

        dto.setSkills(
                "Java,Spring Boot,Microservices");

        restTemplate.put(
                "http://localhost:8080/profile/update/1",
                dto);

        JobSeekerProfileDTO updated =
                service.getProfileById(1);

        assertEquals(
                "Java,Spring Boot,Microservices",
                updated.getSkills());
    }
    @Test
    void deleteProfile() {

        JobSeekerProfileDTO dto =
                new JobSeekerProfileDTO();

        dto.setUserId(6);
        dto.setGender("Female");
        dto.setAddress("Chennai");
        dto.setEducation("B.E CSE");
        dto.setSkills("Testing");
        dto.setExperienceYears(1);
        dto.setCurrentCompany("ABC");
        dto.setCurrentSalary(300000.0);
        dto.setExpectedSalary(500000.0);
        dto.setProfileSummary("Test Profile");

        JobSeekerProfileDTO saved =
                restTemplate.postForObject(
                        "http://localhost:8080/profile/add",
                        dto,
                        JobSeekerProfileDTO.class);

        int profileId =
                saved.getProfileId();

        restTemplate.delete(
                "http://localhost:8080/profile/delete/" + profileId);

        assertThrows(
                Exception.class,
                () -> restTemplate.getForObject(
                        "http://localhost:8080/profile/" + profileId,
                        JobSeekerProfileDTO.class));
    }
    @Test
    void addProfile() {

        JobSeekerProfileDTO dto =
                new JobSeekerProfileDTO();

        dto.setUserId(6);
        dto.setGender("Female");
        dto.setAddress("Chennai");
        dto.setEducation("B.E CSE");
        dto.setSkills("Java,Spring Boot");
        dto.setExperienceYears(2);
        dto.setCurrentCompany("Infosys");
        dto.setCurrentSalary(400000.0);
        dto.setExpectedSalary(600000.0);
        dto.setProfileSummary("Java Developer");

        ResponseEntity<JobSeekerProfileDTO> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/profile/add",
                        dto,
                        JobSeekerProfileDTO.class);

        assertEquals(
                "Java,Spring Boot",
                response.getBody().getSkills());
    }
}