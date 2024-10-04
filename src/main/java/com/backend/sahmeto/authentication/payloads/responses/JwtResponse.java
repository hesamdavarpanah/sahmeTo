package com.backend.sahmeto.authentication.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JwtResponse {

    private String token;

    private final String type = "Bearer";

    private Long id;

    private String username;

    private String phoneNumber;

    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String phoneNumber, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
