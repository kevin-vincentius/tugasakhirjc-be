package com.bcaf.tugasakhir.util;

import com.bcaf.tugasakhir.dto.RespLoginDTO;
import com.bcaf.tugasakhir.handler.ResponseHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalFunction {

    public static ResponseEntity<Object> requestSuccess(Object response, HttpHeaders headers, HttpStatus status, String message) {
        return new ResponseHandler().generateResponse(status, message, response, headers);
    }

    public static ResponseEntity<Object> requestFailed(Object response, HttpStatus status, String message) {
        return new ResponseHandler().generateResponse(status, message, response, null);
    }

}
