package com.hexaware.careercrafter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;
    @ManyToOne
    @JoinColumn(name="profile_id")
    private JobSeekerProfile jobSeeker;
    private LocalDateTime appliedDate;
    private String status;
    private String coverLetter;


}
