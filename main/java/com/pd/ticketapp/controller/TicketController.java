package com.pd.ticketapp.controller;

import com.pd.ticketapp.domain.dto.GetTicketResponseDto;
import com.pd.ticketapp.domain.dto.ListTicketResponseDto;
import com.pd.ticketapp.service.QrCodeService;
import com.pd.ticketapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QrCodeService qrCodeService;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        Page<ListTicketResponseDto> tickets = ticketService.listTicketsForUser(userId,pageable);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        return ticketService.getTicketForUser(userId,ticketId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(userId,ticketId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrCodeImage.length)
                .body(qrCodeImage);
    }
}
