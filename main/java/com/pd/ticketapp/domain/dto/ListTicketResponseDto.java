package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.TicketStatus;

import java.util.UUID;

public record ListTicketResponseDto(
        UUID id,
        TicketStatus status,
        ListTicketTicketTypeResponseDto ticketType
) {
}
