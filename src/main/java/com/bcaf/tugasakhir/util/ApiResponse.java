package com.bcaf.tugasakhir.util;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({"success", "status", "message", "timestamp", "data"})
public class ApiResponse {
    private boolean success;
    private int status;
    private String message;
    private Date timestamp;
    private Object data;

    public ApiResponse(boolean success, int status, String message, Date timestamp, Object data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
