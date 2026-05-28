package com.pd.ticketapp.service;

import com.pd.ticketapp.domain.entity.QrCode;
import com.pd.ticketapp.domain.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
