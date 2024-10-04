package com.backend.sahmeto.authentication.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class OTPValidation {
    @NotBlank
    @Size(min = 11, max = 11)
    private String phoneNumber;

    @NotBlank
    @Size(min = 6, max = 6)
    private int smsCode;

    private Set<String> role;
}
