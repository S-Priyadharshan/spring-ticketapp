package com.pd.ticketapp.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GetPublishedEventDetailsResponseDto(
        UUID id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String venue,
        List<GetPublishedEventTicketTypesDetailsResponseDto> ticketTypes
) {
    public GetPublishedEventDetailsResponseDto{
        if(ticketTypes==null){
            ticketTypes=new ArrayList<>();
        }
    }
}
