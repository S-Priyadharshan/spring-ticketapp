package com.pd.ticketapp.domain.dto;

public record CreateTicketTypeResponseDto(
        String name,
        Double price,
        String description,
        Integer totalAvailable
) {
}
