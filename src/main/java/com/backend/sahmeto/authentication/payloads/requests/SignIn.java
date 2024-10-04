package com.backend.sahmeto.authentication.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignIn {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
