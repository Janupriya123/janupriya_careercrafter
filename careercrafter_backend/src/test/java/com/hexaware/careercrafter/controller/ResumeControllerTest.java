package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ResumeDTO;
import com.hexaware.careercrafter.service.IResumeService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResumeControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IResumeService service;

    @Test
    void getResumeById() {

        ResumeDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/resume/1",
                        ResumeDTO.class);

        assertEquals(1,
                dto.getResumeId());
    }

    @Test
    void getAllResumes() {

        ResponseEntity<ResumeDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/resume/all",
                        ResumeDTO[].class);

        assertTrue(
                response.getBody().length > 0);
    }

    @Test
    void getResumesByProfileId() {

        ResponseEntity<ResumeDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/resume/profile/1",
                        ResumeDTO[].class);

        assertTrue(
                response.getBody().length > 0);
    }
    @Test
    void updateResume() {

        ResumeDTO dto =
                service.getResumeById(1);

        dto.setFileName("UpdatedResume.pdf");
        dto.setUploadedAt(LocalDateTime.now());

        restTemplate.put(
                "http://localhost:8080/resume/update/1",
                dto);

        ResumeDTO updated =
                service.getResumeById(1);

        assertEquals(
                "UpdatedResume.pdf",
                updated.getFileName());
    }
    @Test
    @Disabled
    void deleteResume() {

        ResumeDTO dto = new ResumeDTO();

        dto.setProfileId(1);
        dto.setFileName("DeleteResume.pdf");
        dto.setFilePath("C:/delete.pdf");
        dto.setUploadedAt(LocalDateTime.now());

        ResumeDTO saved =
                restTemplate.postForObject(
                        "http://localhost:8080/resume/upload",
                        dto,
                        ResumeDTO.class);

        int resumeId =
                saved.getResumeId();

        restTemplate.delete(
                "http://localhost:8080/resume/delete/" + resumeId);

        assertThrows(
                Exception.class,
                () -> restTemplate.getForObject(
                        "http://localhost:8080/resume/" + resumeId,
                        ResumeDTO.class));
    }
    @Test
    @Disabled
    void uploadResume() {

        ResumeDTO dto = new ResumeDTO();

        dto.setProfileId(1);
        dto.setFileName("resume.pdf");
        dto.setFilePath("C:/resume.pdf");
        dto.setUploadedAt(LocalDateTime.now());

        ResponseEntity<ResumeDTO> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/resume/upload",
                        dto,
                        ResumeDTO.class);

        assertEquals(
                "resume.pdf",
                response.getBody().getFileName());
    }

}