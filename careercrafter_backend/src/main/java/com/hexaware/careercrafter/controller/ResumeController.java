package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ResumeDTO;
import com.hexaware.careercrafter.service.IResumeService;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private IResumeService service;

    @PostMapping("/upload")
    public ResumeDTO uploadResume(
            @RequestParam("profileId") int profileId,
            @RequestParam("file") MultipartFile file)
    {
        return service.uploadResume(profileId,file);
    }

    @GetMapping("/{resumeId}")
    public ResumeDTO getResumeById(@PathVariable int resumeId) {
        return service.getResumeById(resumeId);
    }

    @GetMapping("/all")
    public List<ResumeDTO> getAllResumes() {

        return service.getAllResumes();
    }

    @GetMapping("/profile/{profileId}")
    public List<ResumeDTO> getResumesByProfileId(
            @PathVariable int profileId) {

        return service.getResumesByProfileId(profileId);
    }

    @PutMapping("/update/{resumeId}")
    public ResumeDTO updateResume(
            @PathVariable int resumeId,
           @Valid @RequestBody ResumeDTO dto) {

        return service.updateResume(resumeId, dto);
    }

    @DeleteMapping("/delete/{resumeId}")
    public void deleteResume(
            @PathVariable int resumeId) {

        service.deleteResume(resumeId);
    }
    @GetMapping("/download/{resumeId}")
    public ResponseEntity<Resource> downloadResume(@PathVariable int resumeId) {

        return service.downloadResume(resumeId);

    }
}