package com.pd.ticketapp.domain.dto;

public record UpdateTicketTypeResponse(
        String name,
        Double price,
        String description,
        Integer totalAvailable
) {
}
