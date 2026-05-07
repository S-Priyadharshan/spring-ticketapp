package com.pd.ticketapp.domain.dto;

public record CreateTicketTypeResponse(
        String name,
        Double price,
        String description,
        Integer totalAvailable
) {
}
