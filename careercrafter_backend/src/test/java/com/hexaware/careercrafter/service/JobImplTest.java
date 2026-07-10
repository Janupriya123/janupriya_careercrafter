package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobDTO;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JobImplTest {

    @Autowired
    private IJobService service;

    @Test
    void addJob() {

        JobDTO dto = new JobDTO();

        dto.setEmployerId(1);
        dto.setTitle("JUnit Service Job");
        dto.setDescription("Testing");
        dto.setLocation("Chennai");
        dto.setExperienceRequired(2);
        dto.setSalaryMin(400000.0);
        dto.setSalaryMax(800000.0);
        dto.setJobType("Full Time");
        dto.setVacancies(2);
        dto.setStatus("OPEN");

        JobDTO result = service.addJob(dto);

        assertEquals(
                "JUnit Service Job",
                result.getTitle());
    }

    @Test
    void getJobById() {

        JobDTO dto = service.getJobById(1);

        assertEquals(1, dto.getJobId());
    }

    @Test
    void getAllJobs() {

        List<JobDTO> jobs =
                service.getAllJobs();

        assertFalse(jobs.isEmpty());
    }

    @Test
    void updateJob() {

        JobDTO dto =
                service.getJobById(2);

        dto.setTitle("Updated Java Developer");
        dto.setVacancies(2);
        dto.setStatus("OPEN");

        JobDTO updated =
                service.updateJob(2, dto);

        assertEquals(
                "Updated Java Developer",
                updated.getTitle());
    }

    @Test
    void deleteJob() {

        JobDTO dto = new JobDTO();

        dto.setEmployerId(1);
        dto.setTitle("Delete Service Job");
        dto.setDescription("Delete Test");
        dto.setLocation("Chennai");
        dto.setExperienceRequired(1);
        dto.setSalaryMin(300000.0);
        dto.setSalaryMax(500000.0);
        dto.setJobType("Full Time");
        dto.setVacancies(1);
        dto.setStatus("OPEN");

        JobDTO saved =
                service.addJob(dto);

        int jobId =
                saved.getJobId();

        service.deleteJob(jobId);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getJobById(jobId));
    }

    @Test
    void getJobsByEmployerId() {

        List<JobDTO> jobs =
                service.getJobsByEmployerId(1);

        assertTrue(jobs.size() > 0);
    }

    @Test
    void searchJobsByTitle() {

        List<JobDTO> jobs =
                service.searchJobsByTitle(
                        "Java Developer");

        assertFalse(jobs.isEmpty());
    }

    @Test
    void searchJobsByLocation() {

        List<JobDTO> jobs =
                service.searchJobsByLocation(
                        "Chennai");

        assertFalse(jobs.isEmpty());
    }

    @Test
    void searchJobsByJobType() {

        List<JobDTO> jobs =
                service.searchJobsByJobType(
                        "Full Time");

        assertFalse(jobs.isEmpty());
    }

    @Test
    void searchJobsByExperience() {

        List<JobDTO> jobs =
                service.searchJobsByExperience(
                        2);

        assertFalse(jobs.isEmpty());
    }
}