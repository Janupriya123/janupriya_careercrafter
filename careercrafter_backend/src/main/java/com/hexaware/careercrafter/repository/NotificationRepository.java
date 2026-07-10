package com.hexaware.careercrafter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.careercrafter.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Integer>{

    List<Notification> findByUserUserIdOrderByCreatedAtDesc(int userId);

}