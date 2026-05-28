package com.pd.ticketapp.service;

import com.pd.ticketapp.domain.dto.*;
import com.pd.ticketapp.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    CreateEventResponse createEvent(UUID organizerId, CreateEventRequest createEventRequest);
    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);
    Optional<Event> getEventForOrganizer(UUID eventId,UUID organizerId);
    UpdateEventResponse updateEvent(UUID userId, UUID eventId, UpdateEventRequest request);
    void deleteEventForOrganizer(UUID eventID,UUID userId);
    Page<ListPublishedEventResponseDto> listPublishedEvents(Pageable pageable);
    Page<ListPublishedEventResponseDto> searchPublishedEvents(String query,Pageable pageable);
    Optional<GetPublishedEventDetailsResponseDto> getPublishedEvent(UUID eventId);
}
