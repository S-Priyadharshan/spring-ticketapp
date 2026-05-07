package com.pd.ticketapp.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="ticket_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketType {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @EqualsAndHashCode.Include
    @Column(name="name",nullable = false,updatable = false)
    private String name;

//    @EqualsAndHashCode.Include
    @Column(name = "total_available",nullable = false)
    private Integer totalAvailable;

//    @EqualsAndHashCode.Include
    @Column(name = "price",nullable = false)
    private Double price;

//    @EqualsAndHashCode.Include
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "ticketType",cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @EqualsAndHashCode.Include
    @CreatedDate
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

//    @EqualsAndHashCode.Include
    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
}
