package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.EventStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UpdateEventRequestDto(
        @NotNull(message = "Event ID must be provided")
        UUID id,

        @NotBlank(message = "Event name must be provided")
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,

        @NotBlank(message = "Event venue must be provided")
        String venue,
        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,

        @NotNull(message = "Event Status must be provided")
        EventStatus status,

        @NotEmpty(message = "Atleast one ticketType must be provided")
        @Valid
        List<UpdateTicketTypeRequest> ticketTypes
) {
    public UpdateEventRequestDto{
        if(ticketTypes==null){
            ticketTypes = new ArrayList<>();
        }
    }
}
