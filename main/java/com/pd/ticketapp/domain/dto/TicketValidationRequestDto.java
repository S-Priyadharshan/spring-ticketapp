package com.pd.ticketapp.domain.dto;


import com.pd.ticketapp.domain.enums.TicketValidationMethod;

import java.util.UUID;

// prone to Client sent inconsistent request data.
public record TicketValidationRequestDto(
        UUID id, // hella overloaded
        TicketValidationMethod method
) {
}
