package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset,Integer> {

    Optional<PasswordReset> findByEmail(String email);

    Optional<PasswordReset> findByEmailAndOtp(String email,String otp);

}