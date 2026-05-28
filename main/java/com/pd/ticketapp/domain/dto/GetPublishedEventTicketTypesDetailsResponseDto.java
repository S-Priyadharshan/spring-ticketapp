package com.pd.ticketapp.domain.dto;

import java.util.UUID;

public record GetPublishedEventTicketTypesDetailsResponseDto(
        UUID id,
        String name,
        Double price,
        String description
) {
}
