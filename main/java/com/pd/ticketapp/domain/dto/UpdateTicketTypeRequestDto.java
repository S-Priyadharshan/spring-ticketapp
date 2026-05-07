package com.pd.ticketapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record UpdateTicketTypeRequestDto(
        UUID Id,

        @NotBlank(message = "Ticket Type name is required")
        String name,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or greater")
        Double price,
        String Description,

        @Positive(message = "Total available cannot be negative")
        Integer totalAvailable
) {
}
