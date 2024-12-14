package com.bcaf.tugasakhir.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "BookingsExtensions")
public class BookingsExtensions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingExtensionId")
    private Integer bookingExtensionId;

    @Column(name = "NewEndTime", nullable = false)
    private LocalDateTime newEndTime;

    @Column(name = "Reason", nullable = false)
    private String reason;

    @CreationTimestamp
    @Column(name = "CreatedAt")
    private Date createdAt;

    public Integer getBookingExtensionId() {
        return bookingExtensionId;
    }

    public void setExtensionId(Integer bookingExtensionId) {
        this.bookingExtensionId = bookingExtensionId;
    }

    public LocalDateTime getNewEndTime() {
        return newEndTime;
    }

    public void setNewEndTime(LocalDateTime newEndTime) {
        this.newEndTime = newEndTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
