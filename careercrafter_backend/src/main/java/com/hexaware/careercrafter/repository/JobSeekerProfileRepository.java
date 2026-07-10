package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {

   JobSeekerProfile findByUserUserId(int userId);

   public List<JobSeekerProfile> findBySkillsContainingIgnoreCase(String skills);

}
