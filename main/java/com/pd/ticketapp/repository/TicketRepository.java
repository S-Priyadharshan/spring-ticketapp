package com.pd.ticketapp.repository;

import com.pd.ticketapp.domain.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    int countByTicketTypeId(UUID ticketTypeId);
    Page<Ticket> findByOwnerId(UUID ownerId, Pageable pageable);
    Optional<Ticket> findByIdAndOwnerId(UUID ticketId,UUID ownerId);
}
