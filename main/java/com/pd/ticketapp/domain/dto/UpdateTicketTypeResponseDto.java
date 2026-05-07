package com.pd.ticketapp.domain.dto;

public record UpdateTicketTypeResponseDto(
        String name,
        Double price,
        String description,
        Integer totalAvailable
) {
}
