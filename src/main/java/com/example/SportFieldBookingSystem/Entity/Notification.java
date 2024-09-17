package com.example.SportFieldBookingSystem.Entity;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.NotificationEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;  // Nội dung thông báo

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private NotificationEnum status;
}
