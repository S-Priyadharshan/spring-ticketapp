package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GetEventDetailsResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStart,
        LocalDateTime salesEnd,
        EventStatus status,
        List<GetEventDetailsTicketTypesResponseDto> ticketTypes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public GetEventDetailsResponseDto{
        if(ticketTypes==null){
            ticketTypes=new ArrayList<>();
        }
    }
}
