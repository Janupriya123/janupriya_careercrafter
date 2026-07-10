package com.hexaware.careercrafter.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;
    @ManyToOne
    @JoinColumn(name="employer_id")
    private Employer employer;
    private String title;
    private String description;
    private String location;
    private Integer experienceRequired;
    private Double salaryMin;
    private Double salaryMax;
    private String jobType;
    private Integer vacancies;
    private LocalDate applicationDeadline;
    private String status;

}