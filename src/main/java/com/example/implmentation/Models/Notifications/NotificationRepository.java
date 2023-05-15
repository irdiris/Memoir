package com.example.implmentation.Models.Notifications;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository  extends JpaRepository<Notifications, Long> {
    List<Notifications> getNotificationsById(int id);
}
