package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.service.IApplicationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationControllerTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IApplicationService service;

    @Test
    void getApplicationById() {

        Long id = 2L;

        ApplicationDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/application/getById/" + id,
                        ApplicationDTO.class);

        assertNotNull(dto);
    }


    @Test
    void getAllApplications() {
        ResponseEntity<ApplicationDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/application/getall",
                        ApplicationDTO[].class);

        assertEquals(200,
                response.getStatusCode().value());
    }

    @Test
    void getApplicationsByJobId() {
        int jobId = 1;

        ResponseEntity<ApplicationDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/application/getByJobId/" + jobId,
                        ApplicationDTO[].class);

        ApplicationDTO[] applications =
                response.getBody();

        assertNotNull(applications);
        assertTrue(applications.length > 0);
    }

    @Test
    void getApplicationsByJobSeekerId() {
        int profileId = 1;

        ResponseEntity<ApplicationDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/application/getByjsid/" + profileId,
                        ApplicationDTO[].class);

        ApplicationDTO[] applications =
                response.getBody();

        assertNotNull(applications);
        assertTrue(applications.length > 0);
    }

    @Test
    void updateApplicationStatus() {
        Long aid = 2L;

        restTemplate.put(
                "http://localhost:8080/application/update/"
                        + aid + "/Approved",
                null);

        ApplicationDTO dto =
                service.getApplicationById(aid);

        assertEquals("Approved",
                dto.getStatus());
    }

    @Test
    @Disabled
    void deleteApplication() {
        Long aid = 1L;

        restTemplate.delete(
                "http://localhost:8080/application/delete/" + aid);

        assertThrows(Exception.class, () -> {

            restTemplate.getForObject(
                    "http://localhost:8080/application/getById/" + aid,
                    ApplicationDTO.class);

        });
    }

    @Test
    @Disabled
    void applyForJob() {
        ApplicationDTO dto = new ApplicationDTO();

        dto.setJobId(1);
        dto.setJobSeekerId(1);
        dto.setStatus("Applied");
        dto.setCoverLetter("Interested");

        ResponseEntity<ApplicationDTO> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/application/create",
                        dto,
                        ApplicationDTO.class);

        ApplicationDTO app = response.getBody();

        assertEquals("Applied", app.getStatus());
    }

}