package com.hexaware.careercrafter.controller;


import com.hexaware.careercrafter.dto.NotificationDTO;
import com.hexaware.careercrafter.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private INotificationService service;

    @PostMapping("/create")
    public NotificationDTO createNotification(
            @RequestBody NotificationDTO dto) {

        return service.createNotification(dto);

    }

    @GetMapping("/user/{userId}")
    public List<NotificationDTO> getNotificationsByUser(
            @PathVariable int userId) {

        return service.getNotificationsByUser(userId);

    }

    @PutMapping("/read/{notificationId}")
    public String markAsRead(
            @PathVariable int notificationId) {

        service.markAsRead(notificationId);

        return "Notification marked as read";

    }
}