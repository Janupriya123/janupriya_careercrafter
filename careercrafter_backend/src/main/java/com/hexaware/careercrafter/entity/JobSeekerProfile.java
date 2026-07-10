package com.hexaware.careercrafter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="job_seeker_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
    private String gender;
    private String address;
    private String education;
    private String skills;
    private Integer experienceYears;
    private String currentCompany;
    private Double  currentSalary;
    private Double  expectedSalary;
    private String profileSummary;
}