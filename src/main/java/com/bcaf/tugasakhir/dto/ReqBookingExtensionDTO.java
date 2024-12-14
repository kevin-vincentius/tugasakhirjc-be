package com.bcaf.tugasakhir.dto;

import java.time.LocalDateTime;

public class ReqBookingExtensionDTO {
    private LocalDateTime newEndTime;
    private String reason;

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
}
