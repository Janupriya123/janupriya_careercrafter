package com.hexaware.careercrafter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="employer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer employerId;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    private String companyName;
    private String website;
    private String industry;
    private String companySize;
    private String companyDescription;
    @OneToMany(mappedBy = "employer")
    private List<Job> jobs;

}