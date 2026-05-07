package com.pd.ticketapp.service.impl;

import com.pd.ticketapp.domain.dto.*;
import com.pd.ticketapp.domain.entity.Event;
import com.pd.ticketapp.domain.entity.TicketType;
import com.pd.ticketapp.domain.entity.User;
import com.pd.ticketapp.exception.EventNotFoundException;
import com.pd.ticketapp.exception.EventUpdateException;
import com.pd.ticketapp.exception.TicketTypeNotFoundException;
import com.pd.ticketapp.exception.UserNotFoundException;
import com.pd.ticketapp.mapper.EventMapper;
import com.pd.ticketapp.repository.EventRepository;
import com.pd.ticketapp.repository.UserRepository;
import com.pd.ticketapp.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    // got to add proper dto mappers here
    public CreateEventResponse createEvent(UUID organizerId, CreateEventRequest req) {

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID %s not found", organizerId)
                ));

        Event event = new Event();
        event.setName(req.name());
        event.setStartDate(req.startDate());
        event.setEndDate(req.endDate());
        event.setVenue(req.venue());
        event.setSalesStartDate(req.salesStartDate());
        event.setSalesEndDate(req.salesEndDate());
        event.setStatus(req.status());
        event.setOrganizer(organizer);

        List<TicketType> tickets = req.ticketTypes().stream()
                .map(t -> {
                    TicketType tt = new TicketType();
                    tt.setName(t.name());
                    tt.setPrice(t.price());
                    tt.setDescription(t.description());
                    tt.setTotalAvailable(t.totalAvailable());
                    tt.setEvent(event);
                    return tt;
                }).toList();

        event.setTicketTypes(tickets);

//        organizer.getOrganizedEvents().add(event); // optional but good

        eventRepository.save(event);

        return eventMapper.toServiceResponse(event);
    }

    @Override
    @Transactional
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId,pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID eventId, UUID organizerId) {
        return eventRepository.findByIdAndOrganizerId(eventId,organizerId);
    }

    @Override
    @Transactional
    public UpdateEventResponse updateEvent(UUID userId, UUID eventId, UpdateEventRequest request) {
        if(request.id()==null){
            throw new EventUpdateException("Event Id cannot be null");
        }
        if(!eventId.equals(request.id())){
            throw new EventUpdateException("Cannot update the id of an Event");
        }

        Event existingEvent = eventRepository.
                findByIdAndOrganizerId(eventId,userId)
                .orElseThrow(()->new EventNotFoundException(
                        String.format("Event with ID %s does not exists",eventId))
                );

        existingEvent.setName(request.name());
        existingEvent.setVenue(request.venue());
        existingEvent.setStartDate(request.startDate());
        existingEvent.setEndDate(request.endDate());
        existingEvent.setSalesStartDate(request.salesStartDate());
        existingEvent.setSalesEndDate(request.salesEndDate());
        existingEvent.setStatus(request.status());

        Set<UUID> requestTicketTypeIds = request.ticketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType->
                !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID,TicketType> existingTicketTypesIndex = existingEvent
                .getTicketTypes()
                .stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity())
                );

        for(UpdateTicketTypeRequest ticketTypeRequest:request.ticketTypes()){
            if(ticketTypeRequest.id()==null){
                TicketType ticketTypeToCreate = TicketType.builder()
                        .name(ticketTypeRequest.name())
                        .totalAvailable(ticketTypeRequest.totalAvailable())
                        .price(ticketTypeRequest.price())
                        .description(ticketTypeRequest.description())
                        .event(existingEvent)
                        .build();
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
            }else if(existingTicketTypesIndex.containsKey(ticketTypeRequest.id())){
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketTypeRequest.id());
                existingTicketType.setName(ticketTypeRequest.name());
                existingTicketType.setPrice(ticketTypeRequest.price());
                existingTicketType.setDescription(ticketTypeRequest.description());
                existingTicketType.setTotalAvailable(ticketTypeRequest.totalAvailable());
            }else{
                throw new TicketTypeNotFoundException(
                        String.format("Ticket Type with ID %s not found",ticketTypeRequest.id()));
            }
        }
        return eventMapper.toUpdateEventResponse(existingEvent);
    }


}
