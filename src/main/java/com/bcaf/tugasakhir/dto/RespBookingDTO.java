package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Rooms;

import java.time.LocalDateTime;
import java.util.Date;

public class RespBookingDTO {
    private Integer bookingId;
    private Rooms room;
    private String bookingType;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Short bookingStatus;
    private Long confirmationCode;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Short getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Short bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Long getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(Long confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
