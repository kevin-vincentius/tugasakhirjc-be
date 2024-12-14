package com.bcaf.tugasakhir.handler;

import com.bcaf.tugasakhir.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseHandler {
    public ResponseEntity<Object> generateResponse(HttpStatus status,
                                                   String message, Object responseObj, HttpHeaders headers) {
//        "success", "status", "message", "timestamp", "data"
        ApiResponse response = new ApiResponse(!status.isError(), status.value(), message, new Date(), responseObj);
        return new ResponseEntity<>(response, headers, status);
    }
}
