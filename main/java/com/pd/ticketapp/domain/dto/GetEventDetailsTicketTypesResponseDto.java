package com.pd.ticketapp.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetEventDetailsTicketTypesResponseDto(
        UUID id,
        String name,
        Double price,
        String description,
        Integer totalAvailable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
