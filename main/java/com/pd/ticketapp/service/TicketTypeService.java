package com.pd.ticketapp.service;

import com.pd.ticketapp.domain.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    public Ticket purchaseTicket(UUID userId,UUID ticketTypeId);
}
