package com.pd.ticketapp.service;

import com.pd.ticketapp.domain.dto.GetTicketResponseDto;
import com.pd.ticketapp.domain.dto.ListTicketResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {
    Page<ListTicketResponseDto> listTicketsForUser(UUID userId, Pageable pageable);
    Optional<GetTicketResponseDto> getTicketForUser(UUID userId,UUID ticketId);
}
