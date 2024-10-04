package com.backend.sahmeto.authentication.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckUserExist {
    @NotBlank
    @Size(min = 11, max = 11)
    private String phoneNumber;

    public CheckUserExist() {
    }

}
