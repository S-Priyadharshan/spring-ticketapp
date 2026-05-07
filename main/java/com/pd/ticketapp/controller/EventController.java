package com.pd.ticketapp.controller;

import com.pd.ticketapp.domain.dto.*;
import com.pd.ticketapp.domain.entity.Event;
import com.pd.ticketapp.mapper.EventMapper;
import com.pd.ticketapp.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ){
        CreateEventRequest createEventRequest= eventMapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());

        CreateEventResponse createEventResponse = eventService.createEvent(userId,createEventRequest);

        CreateEventResponseDto responseDto = eventMapper.toDto(createEventResponse);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    // Data smell using Event need to implement proper DTOs
    public ResponseEntity<Page<ListEventResponseDto>> listEvent(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        Page<Event> eventPage = eventService.listEventsForOrganizer(userId,pageable);

        return new ResponseEntity<>(eventPage.map(eventMapper::toDto),HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventDetails(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        return eventService.getEventForOrganizer(eventId,userId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto request
    ){
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(request);

        UUID userId = UUID.fromString(jwt.getSubject());

        UpdateEventResponse updatedEvent = eventService.updateEvent(userId,eventId,updateEventRequest);

        UpdateEventResponseDto updatedEventDto = eventMapper.toUpdateEventResponseDto(updatedEvent);

        return new ResponseEntity<>(updatedEventDto,HttpStatus.OK);
    }

}
