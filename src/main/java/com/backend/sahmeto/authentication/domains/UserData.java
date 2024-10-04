package com.backend.sahmeto.authentication.domains;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Setter
@Getter
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = UserData.class)
public class UserData implements Serializable {
    private String phoneNumber;
    private int smsCode;
    private String deadTime;

    public UserData() {
    }

    public UserData(String phoneNumber, int smsCode, String deadTime) {
        this.phoneNumber = phoneNumber;
        this.smsCode = smsCode;
        this.deadTime = deadTime;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", smsCode=" + smsCode +
                ", deadTime='" + deadTime + '\'' +
                '}';
    }
}
