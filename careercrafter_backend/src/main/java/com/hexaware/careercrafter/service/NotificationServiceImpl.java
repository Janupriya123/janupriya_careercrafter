package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.NotificationDTO;
import com.hexaware.careercrafter.entity.Notification;
import com.hexaware.careercrafter.entity.User;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.NotificationRepository;
import com.hexaware.careercrafter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements INotificationService{
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Autowired
    private NotificationRepository repo;

    @Autowired
    private UserRepository userRepo;


    @Override
    public NotificationDTO createNotification(NotificationDTO dto) {
        logger.info("Creating notification for userId: {}", dto.getUserId());
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = repo.save(notification);

        dto.setNotificationId(saved.getNotificationId());
        dto.setCreatedAt(saved.getCreatedAt());
        logger.info("Notification created successfully with id: {}", saved.getNotificationId());
        return dto;
    }

    @Override
    public List<NotificationDTO> getNotificationsByUser(int userId) {
        logger.info("Fetching notifications for userId: {}", userId);
        return repo.findByUserUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notification -> {

                    NotificationDTO dto = new NotificationDTO();

                    dto.setNotificationId(notification.getNotificationId());
                    dto.setUserId(notification.getUser().getUserId());
                    dto.setTitle(notification.getTitle());
                    dto.setMessage(notification.getMessage());
                    dto.setRead(notification.isRead());
                    dto.setCreatedAt(notification.getCreatedAt());

                    return dto;

                }).collect(Collectors.toList());

    }

    @Override
    public void markAsRead(int notificationId) {
        logger.info("Marking notification as read with id: {}", notificationId);
        Notification notification = repo.findById(notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found"));

        notification.setRead(true);

        repo.save(notification);

        logger.info("Notification marked as read successfully with id: {}", notificationId);
    }
}
