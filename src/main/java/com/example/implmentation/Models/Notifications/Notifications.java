package com.example.implmentation.Models.Notifications;

import com.example.implmentation.Models.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notifications {
    private int id;
    private String notificationText;
    private User user;
    private String expirationDate;
}
