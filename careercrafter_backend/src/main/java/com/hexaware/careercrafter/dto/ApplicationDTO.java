package com.hexaware.careercrafter.dto;

import com.hexaware.careercrafter.entity.Job;
import com.hexaware.careercrafter.entity.JobSeekerProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long applicationId;
    @NotNull
    private Integer jobId;
    @NotNull(message = "Job Seeker Id is required")
    private Integer jobSeekerId;
    private LocalDateTime appliedDate;
    @NotBlank
    private String status;
    @Size(max = 1000)
    private String coverLetter;
    private String jobTitle;
    private String companyName;
    private String location;
    private Integer resumeId;
    private String resumeFileName;
    private String jobSeekerName;
    private String email;
    private String phoneNumber;
    private String education;
    private Integer experienceYears;
    private String skills;
}
