package com.pd.ticketapp.service.impl;

import com.pd.ticketapp.domain.dto.GetTicketResponseDto;
import com.pd.ticketapp.domain.dto.ListTicketResponseDto;
import com.pd.ticketapp.domain.entity.Ticket;
import com.pd.ticketapp.exception.TicketTypeNotFoundException;
import com.pd.ticketapp.mapper.TicketMapper;
import com.pd.ticketapp.repository.TicketRepository;
import com.pd.ticketapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    @Transactional
    public Page<ListTicketResponseDto> listTicketsForUser(UUID userId, Pageable pageable){
        return ticketRepository.findByOwnerId(userId,pageable)
                .map(ticketMapper::toListTicketResponseDto);
    }

    @Override
    public Optional<GetTicketResponseDto> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndOwnerId(ticketId,userId)
                .map(ticketMapper::toGetTicketResponseDto);
    }
}
