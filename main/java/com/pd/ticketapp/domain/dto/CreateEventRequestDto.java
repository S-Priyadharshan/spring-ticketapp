package com.pd.ticketapp.domain.dto;

import com.pd.ticketapp.domain.enums.EventStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventRequestDto(
        @NotBlank(message = ERROR_MESSAGE_NAME_REQUIRED)
        @Length(max=100,message = ERROR_MESSAGE_NAME_LENGTH)
        String name,

        @NotNull(message = ERROR_MESSAGE_START_DATE_REQUIRED)
        @FutureOrPresent(message = ERROR_MESSAGE_START_DATE_FUTURE)
        LocalDateTime startDate,

        @NotNull(message = ERROR_MESSAGE_END_DATE_REQUIRED)
        LocalDateTime endDate,

        @NotBlank(message = ERROR_MESSAGE_VENUE_REQUIRED)
        String venue,

        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,

        @NotNull(message = ERROR_MESSAGE_STATUS_REQUIRED)
        EventStatus status,

        @Valid
        @Size(min=1,message = ERROR_MESSAGE_TICKETS_EMPTY)
        List<CreateTicketTypeRequestDto> ticketTypes
) {
    public CreateEventRequestDto{
        // we make this final and unmodifiable
        ticketTypes = List.copyOf(ticketTypes);
    }

    private static final String ERROR_MESSAGE_NAME_REQUIRED =
            "Event name is required and cannot be blank";
    private static final String ERROR_MESSAGE_NAME_LENGTH =
            "Event name must be between 3 and 100 characters";
    private static final String ERROR_MESSAGE_VENUE_REQUIRED =
            "Venue location must be specified";

    private static final String ERROR_MESSAGE_START_DATE_FUTURE =
            "Event start date must be in the future";
    private static final String ERROR_MESSAGE_START_DATE_REQUIRED =
            "Event start date cannot be blank";
    private static final String ERROR_MESSAGE_END_DATE_REQUIRED =
            "Event end date cannot be blank";


    private static final String ERROR_MESSAGE_STATUS_REQUIRED =
            "A valid event status (e.g., DRAFT, PUBLISHED) must be provided";
    private static final String ERROR_MESSAGE_TICKETS_EMPTY =
            "At least one ticket type must be defined for the event";
}
