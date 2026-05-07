package com.pd.ticketapp.mapper;

import com.pd.ticketapp.domain.dto.*;
import com.pd.ticketapp.domain.entity.Event;
import com.pd.ticketapp.domain.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateEventRequest fromDto(CreateEventRequestDto dto);
    CreateEventResponseDto toDto(CreateEventResponse response);
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);
    CreateTicketTypeResponseDto toDto(CreateTicketTypeResponse response);

    Event toEntity(CreateEventRequest request);

    CreateEventResponse toServiceResponse(Event event);

    TicketType toEntity(CreateTicketTypeRequest request);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toDto(Event event);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    GetEventDetailsTicketTypesResponseDto toGetEventDetailsTicketTypesResponseDto(TicketType ticketType);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventResponse toUpdateEventResponse(Event event);

    UpdateEventResponseDto toUpdateEventResponseDto(UpdateEventResponse response);

    UpdateTicketTypeResponseDto toUpdateEventTicketTypeResponseDto(UpdateTicketTypeResponse response);
}
