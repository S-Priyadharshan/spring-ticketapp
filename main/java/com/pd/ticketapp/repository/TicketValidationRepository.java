package com.pd.ticketapp.repository;

import com.pd.ticketapp.domain.entity.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}
