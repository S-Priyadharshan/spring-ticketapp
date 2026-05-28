package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetTicketResponseDto(
        UUID id,
        TicketStatus status,
        Double price,
        String description,
        String name,
        String venue,
        LocalDateTime eventStartDate,
        LocalDateTime eventEndDate
) {
}
