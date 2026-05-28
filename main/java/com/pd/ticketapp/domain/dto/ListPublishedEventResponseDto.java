package com.pd.ticketapp.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListPublishedEventResponseDto(
        UUID id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String venue
) {
}
