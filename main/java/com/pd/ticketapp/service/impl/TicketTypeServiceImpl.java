package com.pd.ticketapp.service.impl;

import com.pd.ticketapp.domain.entity.Ticket;
import com.pd.ticketapp.domain.entity.TicketType;
import com.pd.ticketapp.domain.entity.User;
import com.pd.ticketapp.domain.enums.TicketStatus;
import com.pd.ticketapp.exception.TicketSoldOutException;
import com.pd.ticketapp.exception.TicketTypeNotFoundException;
import com.pd.ticketapp.exception.UserNotFoundException;
import com.pd.ticketapp.repository.TicketRepository;
import com.pd.ticketapp.repository.TicketTypeRepository;
import com.pd.ticketapp.repository.UserRepository;
import com.pd.ticketapp.service.QrCodeService;
import com.pd.ticketapp.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("User with ID %s was not found",userId)
                ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(()-> new TicketTypeNotFoundException(
                        String.format("Ticket type of ID %s not found",ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketTypeId);
        int totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable){
            throw new TicketSoldOutException("Tickets sold out");
        }

        Ticket ticket = Ticket.builder()
                .status(TicketStatus.PURCHASED)
                .ticketType(ticketType)
                .owner(user)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return savedTicket;
    }
}
