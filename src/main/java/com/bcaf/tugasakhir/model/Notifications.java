package com.bcaf.tugasakhir.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "Notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationId")
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "UserId", foreignKey = @ForeignKey(name = "fk-notif-to-user"), nullable = false)
    private MstUser userId;

    @ManyToOne
    @JoinColumn(name = "BookingId", foreignKey = @ForeignKey(name = "fk-to-booking"), nullable = false)
    private Bookings bookingId;

    @Column(name = "NotificationTime", nullable = false)
    private Date notificationTime;

    @Column(name = "Message", nullable = false)
    private String message;

    @Column(name = "IsRead", nullable = false)
    private Boolean isRead = false;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public MstUser getUserId() {
        return userId;
    }

    public void setUserId(MstUser userId) {
        this.userId = userId;
    }

    public Bookings getBookingId() {
        return bookingId;
    }

    public void setBookingId(Bookings bookingId) {
        this.bookingId = bookingId;
    }

    public Date getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
