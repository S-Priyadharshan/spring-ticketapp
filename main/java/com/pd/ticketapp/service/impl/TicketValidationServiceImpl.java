package com.pd.ticketapp.service.impl;

import com.pd.ticketapp.domain.entity.QrCode;
import com.pd.ticketapp.domain.entity.Ticket;
import com.pd.ticketapp.domain.entity.TicketValidation;
import com.pd.ticketapp.domain.enums.QrCodeStatus;
import com.pd.ticketapp.domain.enums.TicketValidationMethod;
import com.pd.ticketapp.domain.enums.TicketValidationStatus;
import com.pd.ticketapp.exception.QrCodeNotFoundException;
import com.pd.ticketapp.exception.TicketNotFoundException;
import com.pd.ticketapp.repository.QrCodeRepository;
import com.pd.ticketapp.repository.TicketRepository;
import com.pd.ticketapp.repository.TicketValidationRepository;
import com.pd.ticketapp.service.TicketValidationService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatus.ACTIVE)
                .orElseThrow(()-> new QrCodeNotFoundException(
                        String.format("QR code with ID %s was not found",qrCodeId)
                ));
        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket);
    }

    private TicketValidation validateTicket(Ticket ticket){
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethod.QR_SCAN);

        TicketValidationStatus ticketValidationStatus = ticket.getValidations().stream()
                .filter(v-> TicketValidationStatus.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v->TicketValidationStatus.INVALID)
                .orElse(TicketValidationStatus.VALID);

        ticketValidation.setStatus(ticketValidationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new TicketNotFoundException(
                        String.format("Ticket with ID %s was not found",ticketId)
                ));
        return validateTicket(ticket);
    }
}
