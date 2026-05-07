package com.pd.ticketapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record CreateTicketTypeRequestDto(
        @NotBlank(message = ERROR_MESSAGE_NAME_REQUIRED)
        @Length(max=20,message = ERROR_MESSAGE_NAME_LENGTH)
        String name,

        @NotNull(message = ERROR_MESSAGE_PRICE_REQUIRED)
        @Positive(message = ERROR_MESSAGE_PRICE_POSITIVE)
        Double price,

        @Length(max=255,message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
        String description,

        @NotNull(message = ERROR_MESSAGE_TOTAL_AVAILABLE_REQUIRED)
        @PositiveOrZero(message = ERROR_MESSAGE_TOTAL_AVAILABLE_VALID)
        Integer totalAvailable
) {
    private static final String ERROR_MESSAGE_NAME_REQUIRED =
            "Ticket type name is required";

    private static final String ERROR_MESSAGE_NAME_LENGTH =
            "Ticket type name must not exceed 20 characters";

    private static final String ERROR_MESSAGE_PRICE_REQUIRED =
            "Ticket price is required";

    private static final String ERROR_MESSAGE_PRICE_POSITIVE =
            "Ticket price must be greater than zero";

    private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH =
            "Description must not exceed 255 characters";

    private static final String ERROR_MESSAGE_TOTAL_AVAILABLE_REQUIRED =
            "Total available tickets must be specified";

    private static final String ERROR_MESSAGE_TOTAL_AVAILABLE_VALID =
            "Total available tickets cannot be negative";
}
