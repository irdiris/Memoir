package com.example.implmentation.Models.Notifications;

import com.example.implmentation.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Notification")
public class Notifications {
    @Id
    @SequenceGenerator(name = "Notification_Sequence", sequenceName = "Notification_Sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Notification_Sequence")
    private int id;
    private String notificationText;
    @ManyToOne
    @JoinColumn(name = "user", foreignKey = @ForeignKey(name = "fk_user_Not"), referencedColumnName = "id")
    private User user;
    private String expirationDate;
}
