package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.BookingsExtensions;
import com.bcaf.tugasakhir.model.MstUser;
import com.bcaf.tugasakhir.model.Rooms;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RespGetMyBookingsDTO {
    private Long bookingId;
    private BookingsExtensions bookingsExtensions;
    private Rooms room;
    @JsonProperty("user")
    private RespListUserDTO userDTO;
    private String bookingType;
    private String description;
    private LocalDate bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bookingStatus;
    private Long confirmationCode;

    public Long getBookingId() {
        return bookingId;
    }

    public RespListUserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(RespListUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public BookingsExtensions getBookingsExtensions() {
        return bookingsExtensions;
    }

    public void setBookingsExtensions(BookingsExtensions bookingsExtensions) {
        this.bookingsExtensions = bookingsExtensions;
    }

    public void setBookingId(Long bookingId) {
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

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Long getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(Long confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

}
