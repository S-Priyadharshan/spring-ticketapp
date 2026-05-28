package com.pd.ticketapp.mapper;

import com.pd.ticketapp.domain.dto.GetTicketResponseDto;
import com.pd.ticketapp.domain.dto.ListTicketResponseDto;
import com.pd.ticketapp.domain.dto.ListTicketTicketTypeResponseDto;
import com.pd.ticketapp.domain.entity.Ticket;
import com.pd.ticketapp.domain.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price",source = "ticket.ticketType.price")
    @Mapping(target = "description",source = "ticket.ticketType.description")
    @Mapping(target = "name",source = "ticket.ticketType.event.name")
    @Mapping(target = "venue",source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStartDate",source = "ticket.ticketType.event.startDate")
    @Mapping(target = "eventEndDate",source = "ticket.ticketType.event.endDate")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}
