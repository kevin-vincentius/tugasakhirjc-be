package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.BookingsExtensions;
import com.bcaf.tugasakhir.model.MstUser;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class RespGetSchedulesDTO {
    private Integer bookingId;
    private BookingsExtensions bookingsExtensions;
    private String bookingType;
    private String description;
    private LocalDate bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bookingStatus;
    @JsonProperty("user")
    private RespListUserDTO userDTO;


    public RespListUserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(RespListUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public BookingsExtensions getBookingsExtensions() {
        return bookingsExtensions;
    }

    public void setBookingsExtensions(BookingsExtensions bookingsExtensions) {
        this.bookingsExtensions = bookingsExtensions;
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

}
