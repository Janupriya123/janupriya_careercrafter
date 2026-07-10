package com.hexaware.careercrafter.dto;

import com.hexaware.careercrafter.entity.JobSeekerProfile;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private Integer resumeId;
    private Integer profileId;

    @NotBlank
    private String fileName;

    @NotBlank
    private String filePath;


    private LocalDateTime uploadedAt;
}
