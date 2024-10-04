package com.backend.sahmeto.products.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateProduct {

    @NotBlank
    @Size(max = 64, min = 5)
    private String name;

    @Size(min = 5)
    private String description;

    @Size(max = 128, min = 7)
    private String productImagePath;

    @NotBlank
    private int basePrice;

    @NotBlank
    private int lowBet;

    @NotBlank
    private LocalDateTime lotteryDate;

    @NotBlank
    private Long categoryId;
}
