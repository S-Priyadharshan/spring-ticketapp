package com.pd.ticketapp.controller;

import com.pd.ticketapp.domain.dto.GetPublishedEventDetailsResponseDto;
import com.pd.ticketapp.domain.dto.ListPublishedEventResponseDto;
import com.pd.ticketapp.mapper.EventMapper;
import com.pd.ticketapp.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable
    ){
        Page<ListPublishedEventResponseDto> events;
        if(null!=q && !q.trim().isEmpty()){
            events= eventService.searchPublishedEvents(q,pageable);
        }else{
            events = eventService.listPublishedEvents(pageable);
        }

        return new ResponseEntity<>(events,HttpStatus.OK);
    }

    @GetMapping(path="/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventsDetails(
            @PathVariable UUID eventId
    ){
        Optional<GetPublishedEventDetailsResponseDto> publishedEventDetails = eventService.getPublishedEvent(eventId);
        return publishedEventDetails
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
