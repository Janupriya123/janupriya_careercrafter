package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Integer> {
    List<Resume> findByJobSeekerProfileId(int profileId);
}
