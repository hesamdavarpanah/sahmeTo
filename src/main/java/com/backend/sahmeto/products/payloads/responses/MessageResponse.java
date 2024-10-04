package com.backend.sahmeto.products.payloads.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class MessageResponse {
    public static ResponseEntity<Object> generateResponse(String title, String message, HttpStatus status){
        return com.backend.sahmeto.authentication.payloads.responses.MessageResponse.generateResponse(title, message, status);
    }
}
