package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ResumeDTO;
import com.hexaware.careercrafter.entity.JobSeekerProfile;
import com.hexaware.careercrafter.entity.Resume;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobSeekerProfileRepository;
import com.hexaware.careercrafter.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ResumeServiceImpl implements IResumeService {
    private static final Logger logger = LoggerFactory.getLogger(ResumeServiceImpl.class);
    @Autowired
    private ResumeRepository repo;
    @Autowired
    private JobSeekerProfileRepository pr;

    @Override
    public ResumeDTO uploadResume(int profileId, MultipartFile file) {
        logger.info("Uploading resume for profileId: {}", profileId);
        try {

            JobSeekerProfile profile = pr.findById(profileId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Profile not found"));

            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Resume resume;

            List<Resume> resumes = repo.findByJobSeekerProfileId(profileId);

            if (resumes.isEmpty()) {

                resume = new Resume();
                resume.setJobSeeker(profile);

            } else {

                resume = resumes.get(0);

            }

            resume.setFileName(fileName);
            resume.setFilePath(filePath.toString());
            resume.setUploadedAt(LocalDateTime.now());

            Resume saved = repo.save(resume);
            logger.info("Resume uploaded successfully for profileId: {}", profileId);
            ResumeDTO dto = new ResumeDTO();

            dto.setResumeId(saved.getResumeId());
            dto.setProfileId(saved.getJobSeeker().getProfileId());
            dto.setFileName(saved.getFileName());
            dto.setFilePath(saved.getFilePath());
            dto.setUploadedAt(saved.getUploadedAt());

            return dto;

        } catch (Exception e) {
            logger.error("Resume upload failed for profileId: {}", profileId, e);
            e.printStackTrace();
            throw new RuntimeException("Resume upload failed: " + e.getMessage());

        }
    }
    @Override
    public ResumeDTO getResumeById(int resumeId) {
        logger.info("Fetching resume with id: {}", resumeId);
        Resume r=repo.findById(resumeId).orElseThrow(() -> new  ResourceNotFoundException("Resume not found"));

        logger.info("Resume retrieved successfully with id: {}", resumeId);
        return convertToDTO(r);
    }

    @Override
    public List<ResumeDTO> getAllResumes() {
        logger.info("Fetching all resumes");
        List<ResumeDTO> list = new ArrayList<>();

        List<Resume> resumes = repo.findAll();

        for(Resume r : resumes)
        {
            list.add(convertToDTO(r));
        }

        logger.info("Total resumes found: {}", resumes.size());
        return list;
    }

    @Override
    public List<ResumeDTO> getResumesByProfileId(int profileId) {
        logger.info("Fetching resumes for profileId: {}", profileId);

        List<ResumeDTO> list=new ArrayList<>();

        List<Resume> resumes=repo.findByJobSeekerProfileId(profileId);

        for(Resume r : resumes)
        {
            list.add(convertToDTO(r));
        }
        logger.info("Found {} resumes for profileId: {}", resumes.size(), profileId);
        return list;
    }

    @Override
    public ResumeDTO updateResume(int resumeId, ResumeDTO dto)  {
        logger.info("Updating resume with id: {}", resumeId);
            Resume r = repo.findById(resumeId).orElseThrow(() ->new  ResourceNotFoundException("Resume not found"));
            r.setFileName(dto.getFileName());
            r.setFilePath(dto.getFilePath());
            Resume saved = repo.save(r);
        logger.info("Resume updated successfully with id: {}", resumeId);
            return convertToDTO(saved);
    }

    @Override
    public void deleteResume(int resumeId) {
        logger.info("Deleting resume with id: {}", resumeId);
        Resume resume = repo.findById(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resume not found"));

        try {

            Path path = Paths.get(resume.getFilePath());

            Files.deleteIfExists(path);

        }
        catch (Exception e) {
            logger.error("Unable to delete resume file with id: {}", resumeId, e);

            throw new RuntimeException("Unable to delete resume file");

        }

        repo.delete(resume);
        logger.info("Resume file deleted from storage");

    }

        public  ResumeDTO convertToDTO(Resume r) {

            ResumeDTO d = new ResumeDTO();

            d.setResumeId(r.getResumeId());
            d.setProfileId(
                    r.getJobSeeker().getProfileId());

            d.setFileName(r.getFileName());
            d.setFilePath(r.getFilePath());
            d.setUploadedAt(r.getUploadedAt());

            return d;
        }
    @Override
    public ResponseEntity<Resource> downloadResume(int resumeId) {
        logger.info("Downloading resume with id: {}", resumeId);
        try {

            Resume resume = repo.findById(resumeId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Resume not found"));

            Path path = Paths.get(resume.getFilePath());

            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resume.getFileName() + "\"")
                    .body(resource);

        }
        catch (Exception e) {
            logger.error("Unable to download resume with id: {}", resumeId, e);

            throw new RuntimeException("Unable to download resume");

        }

    }

}
