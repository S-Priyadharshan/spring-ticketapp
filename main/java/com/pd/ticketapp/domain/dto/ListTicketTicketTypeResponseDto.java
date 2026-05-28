package com.pd.ticketapp.domain.dto;

import java.util.UUID;

public record ListTicketTicketTypeResponseDto(
        UUID id,
        String name,
        Double price
) {
}
