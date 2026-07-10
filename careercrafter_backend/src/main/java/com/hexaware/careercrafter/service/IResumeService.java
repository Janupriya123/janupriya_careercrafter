package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ResumeDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IResumeService {
    ResumeDTO uploadResume(int profileId,
                           MultipartFile file);
    ResumeDTO getResumeById(int resumeId);
    List<ResumeDTO> getAllResumes();
    List<ResumeDTO> getResumesByProfileId(int profileId);
    ResumeDTO updateResume(int resumeId, ResumeDTO dto);
    void deleteResume(int resumeId);
    ResponseEntity<Resource> downloadResume(int resumeId);
}
