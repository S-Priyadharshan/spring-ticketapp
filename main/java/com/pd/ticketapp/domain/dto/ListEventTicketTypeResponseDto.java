package com.pd.ticketapp.domain.dto;

import java.util.UUID;

public record ListEventTicketTypeResponseDto(
        UUID id,
        String name,
        Double price,
        String description,
        Integer totalAvailable
) {
}
