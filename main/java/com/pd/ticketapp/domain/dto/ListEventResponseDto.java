package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ListEventResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStart,
        LocalDateTime salesEnd,
        EventStatus status,
        List<ListEventTicketTypeResponseDto> ticketTypes
) {
    public ListEventResponseDto{
        if(ticketTypes==null){
            ticketTypes = new ArrayList<>();
        }
    }
}
