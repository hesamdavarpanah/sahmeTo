package com.backend.sahmeto.authentication.validators;

import com.backend.sahmeto.authentication.services.RedisReceiver;
import com.backend.sahmeto.authentication.payloads.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CheckUserExistValidator {
    @Autowired
    private RedisReceiver redisReceiver;

    public ResponseEntity<Object> checkPhoneNumber(String phoneNumber) {
        Object userDataReceived = redisReceiver.getData(phoneNumber);
        if (userDataReceived != null) {
            LocalDateTime deadTime = LocalDateTime.parse(userDataReceived.toString().substring(62, 91));
            if (LocalDateTime.now().isBefore(deadTime)){
                return MessageResponse.generateResponse("please wait!", "you could send request every 2 minutes, please wait a little bit!", HttpStatus.TOO_EARLY);
            }
        }
        if (phoneNumber.length() != 11) {
            return MessageResponse.generateResponse("invalid phoneNumber", "the phone number should be 11 digits", HttpStatus.LENGTH_REQUIRED);
        }
        if (!phoneNumber.startsWith("09")) {
            return MessageResponse.generateResponse("invalid phoneNumber", "the phone number should start with 09", HttpStatus.NOT_ACCEPTABLE);
        }
        return MessageResponse.generateResponse("pass", "pass", HttpStatus.OK);
    }
}
