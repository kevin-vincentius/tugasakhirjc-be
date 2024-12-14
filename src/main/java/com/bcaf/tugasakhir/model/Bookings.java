package com.bcaf.tugasakhir.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingId")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "UserId", foreignKey = @ForeignKey(name = "fk-booking-to-user"), nullable = false)
    private MstUser user;

    @OneToOne
    @JoinColumn(name = "BookingExtensionId", foreignKey = @ForeignKey(name = "fk-booking-to-extension"))
    private BookingsExtensions bookingsExtensions;

    @ManyToOne
    @JoinColumn(name = "RoomNumber", foreignKey = @ForeignKey(name = "fk-booking-to-room"), nullable = false)
    private Rooms room;

    @Column(name = "BookingType", nullable = false)
    private String bookingType; // --> Meeting/ Training

    @Column(name = "Description")
    private String description;

    @Column(name = "BookingDate")
    private LocalDate bookingDate = LocalDate.now();

    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "EndTime", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "BookingStatus", nullable = false)
    private Short bookingStatus = 0; // 0 (booked), 1 (ongoing), 2 (completed), 3 (canceled)

    @Column(name = "ConfirmationCode")
    private Long confirmationCode;

    @CreationTimestamp
    @Column(name = "CreatedAt", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "ModifiedAt", insertable = false)
    private Date modifiedAt;

    public Integer getBookingId() {
        return bookingId;
    }

    public BookingsExtensions getBookingsExtensions() {
        return bookingsExtensions;
    }

    public void setBookingsExtensions(BookingsExtensions bookingsExtensions) {
        this.bookingsExtensions = bookingsExtensions;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public MstUser getUser() {
        return user;
    }

    public void setUser(MstUser user) {
        this.user = user;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
