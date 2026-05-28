package com.pd.ticketapp.domain.dto;
import com.pd.ticketapp.domain.enums.TicketValidationStatus;

import java.util.UUID;

public record TicketValidationResponseDto(
        UUID ticketId,
        TicketValidationStatus status
) {
}
