package com.backend.sahmeto.authentication.validators;

import com.backend.sahmeto.authentication.services.RedisReceiver;
import com.backend.sahmeto.authentication.payloads.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CheckOTPValidationValidator {
    @Autowired
    private RedisReceiver redisReceiver;

    public ResponseEntity<Object> checkOTP(String phoneNumber, int smsCode) {
        Object userDataReceived = redisReceiver.getData(phoneNumber);
        if (phoneNumber.length() != 11) {
            return MessageResponse.generateResponse("invalid phoneNumber", "the phone number should be 11 digits", HttpStatus.LENGTH_REQUIRED);
        }
        else if (!phoneNumber.startsWith("09")) {
            return MessageResponse.generateResponse("invalid phoneNumber", "the phone number should start with 09", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (userDataReceived == null) {
            return MessageResponse.generateResponse("invalid phoneNumber", "the phone number could not be found, please use the correct number that used in check user exist or forget password", HttpStatus.NOT_ACCEPTABLE);
        }
        int userDataSMSCode = Integer.parseInt(userDataReceived.toString().substring(44, 50));
        if (smsCode != userDataSMSCode) {
            return MessageResponse.generateResponse("invalid smsCode", "the sms code is not valid, please use the correct sms code or request sms code again", HttpStatus.NOT_ACCEPTABLE);
        }
        return MessageResponse.generateResponse("pass", "pass", HttpStatus.OK);
    }
}
