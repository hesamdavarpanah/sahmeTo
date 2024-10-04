package com.backend.sahmeto.products.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategory {
    @NotBlank
    @Size(min = 5, max = 64)
    private String name;

    @Size(min = 5)
    private String description;

    @Size(max = 128, min = 7)
    private String categoryImagePath;
}
