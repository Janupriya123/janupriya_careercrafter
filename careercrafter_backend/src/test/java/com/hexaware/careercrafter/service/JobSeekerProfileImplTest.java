package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobSeekerProfileDTO;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class JobSeekerProfileImplTest
{
    @Autowired
    private IJobSeekerProfileService service;

    @Test
    void createProfile() {
        JobSeekerProfileDTO dto =
                new JobSeekerProfileDTO();

        dto.setUserId(4);
        dto.setGender("Female");
        dto.setAddress("Chennai");
        dto.setEducation("B.E CSE");
        dto.setSkills("Java,Spring Boot");
        dto.setExperienceYears(2);
        dto.setCurrentCompany("Infosys");
        dto.setCurrentSalary(400000.0);
        dto.setExpectedSalary(600000.0);
        dto.setProfileSummary("Java Developer");

        JobSeekerProfileDTO saved =
                service.createProfile(dto);

        assertEquals(
                "Java,Spring Boot",
                saved.getSkills());
    }

    @Test
    void getProfileById() {
        JobSeekerProfileDTO dto =
                service.getProfileById(1);

        assertEquals(
                1,
                dto.getProfileId());
    }

    @Test
    void getAllProfiles() {

        List<JobSeekerProfileDTO> list =
                service.getAllProfiles();

        assertFalse(
                list.isEmpty());
    }

    @Test
    void updateProfile() {
        JobSeekerProfileDTO dto =
                service.getProfileById(1);

        dto.setSkills(
                "Java,Spring Boot,Microservices");

        JobSeekerProfileDTO updated =
                service.updateProfile(
                        1,
                        dto);

        assertEquals(
                "Java,Spring Boot,Microservices",
                updated.getSkills());
    }

    @Test
    void deleteProfile() {
        JobSeekerProfileDTO dto =
                new JobSeekerProfileDTO();

        dto.setUserId(4);
        dto.setGender("Female");
        dto.setAddress("Chennai");
        dto.setEducation("B.E CSE");
        dto.setSkills("Testing");
        dto.setExperienceYears(1);
        dto.setCurrentCompany("ABC");
        dto.setCurrentSalary(300000.0);
        dto.setExpectedSalary(500000.0);
        dto.setProfileSummary("Delete Test");

        JobSeekerProfileDTO saved =
                service.createProfile(dto);

        int profileId =
                saved.getProfileId();

        service.deleteProfile(profileId);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getProfileById(profileId));
    }

    @Test
    void getProfileByUserId() {
        JobSeekerProfileDTO dto =
                service.getProfileByUserId(102);

        assertEquals(
                102,
                dto.getUserId());
    }

    @Test
    void searchBySkills() {

        List<JobSeekerProfileDTO> list =
                service.searchBySkills("Java");

        assertFalse(
                list.isEmpty());
    }
}