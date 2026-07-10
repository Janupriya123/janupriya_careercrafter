package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    public List<Application> findByJobJobId(int id);
    public List<Application>  findByJobSeekerProfileId(int profileId);
    @Query("SELECT a FROM Application a WHERE a.job.jobId=:jobId AND a.jobSeeker.profileId=:profileId")
    List<Application> findExistingApplications(@Param("jobId") int jobId,
                                               @Param("profileId") int profileId);
    boolean existsByJobJobId(int jobId);
}
