package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ResumeDTO;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ResumeImplTest {

    @Autowired
    private IResumeService service;
    @Disabled
    @Test
    void uploadResume() {
        ResumeDTO dto = new ResumeDTO();

        dto.setProfileId(1);
        dto.setFileName("resume.pdf");
        dto.setFilePath("C:/resume.pdf");
        dto.setUploadedAt(LocalDateTime.now());

      //  ResumeDTO saved =
          //      service.uploadResume(dto);

        //assertEquals(
             //   "resume.pdf",
              //  saved.getFileName());
    }

    @Test
    void getResumeById() {

        ResumeDTO dto =
                service.getResumeById(1);

        assertEquals(
                1,
                dto.getResumeId());
    }

    @Test
    void getAllResumes() {
        List<ResumeDTO> list =
                service.getAllResumes();

        assertFalse(
                list.isEmpty());
    }

    @Test
    void getResumesByProfileId() {
        List<ResumeDTO> list =
                service.getResumesByProfileId(1);

        assertFalse(
                list.isEmpty());
    }

    @Test
    void updateResume() {
        ResumeDTO dto =
                service.getResumeById(1);

        dto.setFileName(
                "UpdatedResume.pdf");

        dto.setUploadedAt(
                LocalDateTime.now());

        ResumeDTO updated =
                service.updateResume(
                        1,
                        dto);

        assertEquals(
                "UpdatedResume.pdf",
                updated.getFileName());
    }
    @Disabled
    @Test
    void deleteResume() {

        ResumeDTO dto = new ResumeDTO();

        dto.setProfileId(1);
        dto.setFileName("DeleteResume.pdf");
        dto.setFilePath("C:/delete.pdf");
        dto.setUploadedAt(LocalDateTime.now());

       // ResumeDTO saved =
          //      service.uploadResume(dto);

    //    int resumeId =
            //    saved.getResumeId();

     //   service.deleteResume(resumeId);

        //assertThrows(
        //        ResourceNotFoundException.class,
              //  () -> service.getResumeById(resumeId));
   }


}