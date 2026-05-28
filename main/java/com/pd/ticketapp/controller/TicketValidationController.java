package com.pd.ticketapp.controller;

import com.pd.ticketapp.domain.dto.TicketValidationRequestDto;
import com.pd.ticketapp.domain.dto.TicketValidationResponseDto;
import com.pd.ticketapp.domain.entity.TicketValidation;
import com.pd.ticketapp.domain.enums.TicketValidationMethod;
import com.pd.ticketapp.mapper.TicketValidationMapper;
import com.pd.ticketapp.repository.TicketValidationRepository;
import com.pd.ticketapp.service.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
    ){
        TicketValidationMethod ticketValidationMethod = ticketValidationRequestDto.method();
        TicketValidation ticketValidation;
        if(TicketValidationMethod.MANUAL.equals(ticketValidationMethod)){
            ticketValidation = ticketValidationService.validateTicketManually(ticketValidationRequestDto.id());
        }else{
            ticketValidation = ticketValidationService.validateTicketByQrCode(ticketValidationRequestDto.id());
        }

        return new ResponseEntity<>(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation), HttpStatus.OK);
    }
}
