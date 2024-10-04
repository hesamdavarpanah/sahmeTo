package com.backend.sahmeto.products.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ConfirmProduct {

    @NotBlank
    private boolean isConfirmed;


    public @NotBlank boolean getIsConfirmed() {
        return isConfirmed;
    }

}
