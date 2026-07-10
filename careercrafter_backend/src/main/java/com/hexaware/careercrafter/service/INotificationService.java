package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.NotificationDTO;

import java.util.List;

public interface INotificationService {
    NotificationDTO createNotification(NotificationDTO dto);

    List<NotificationDTO> getNotificationsByUser(int userId);

    void markAsRead(int notificationId);
}
