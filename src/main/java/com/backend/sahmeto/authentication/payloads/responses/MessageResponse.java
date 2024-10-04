package com.backend.sahmeto.authentication.payloads.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class MessageResponse {
    public static ResponseEntity<Object> generateResponse(String title, String message, HttpStatus status){
        Map<String, Object> response = new HashMap<>();
        response.put("title", title);
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }
}
