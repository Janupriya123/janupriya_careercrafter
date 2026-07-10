package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {

    List<Job> findByEmployerEmployerId(int employerId);
    List<Job> findByTitleContainingIgnoreCase(String title);
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findByJobTypeIgnoreCase(String jobType);
    List<Job> findByExperienceRequired(int experience);

    List<Job> findByLocationAndExperienceRequiredLessThanEqual(
            String location,
            Integer experienceRequired);
    @Query("""
SELECT j FROM Job j
WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :skill, '%'))
   OR LOWER(j.description) LIKE LOWER(CONCAT('%', :skill, '%'))
""")
    List<Job> findRecommendedJobs(@Param("skill") String skill);

}
