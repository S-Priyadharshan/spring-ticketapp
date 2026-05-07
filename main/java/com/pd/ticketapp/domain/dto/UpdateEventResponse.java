package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record UpdateEventResponse(
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String venue,
        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,
        EventStatus status,
        List<UpdateTicketTypeRequest> ticketTypes
) {
    public UpdateEventResponse{
        if(ticketTypes==null){
            ticketTypes = new ArrayList<>();
        }
    }
}
