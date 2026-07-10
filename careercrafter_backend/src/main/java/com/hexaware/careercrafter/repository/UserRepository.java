package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByEmailAndPassword(String email, String password);
    public User findByEmail(String email);
}
