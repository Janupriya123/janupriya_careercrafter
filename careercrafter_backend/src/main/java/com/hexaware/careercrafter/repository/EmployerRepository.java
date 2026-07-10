package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,Integer> {
    Employer findByUserUserId(int userId);
}
