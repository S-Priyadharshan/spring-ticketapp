package com.pd.ticketapp.domain.entity;

import com.pd.ticketapp.domain.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

//    @EqualsAndHashCode.Include
    @Column(name = "name",nullable = false)
    private String name;

//    @EqualsAndHashCode.Include
    @Column(name="event_start")
    private LocalDateTime startDate;

//    @EqualsAndHashCode.Include
    @Column(name="event_end")
    private LocalDateTime endDate;

//    @EqualsAndHashCode.Include
    @Column(name = "venue",nullable = false)
    private String venue;

//    @EqualsAndHashCode.Include
    @Column(name="sales_start")
    private LocalDateTime salesStartDate;

//    @EqualsAndHashCode.Include
    @Column(name="sales_end")
    private LocalDateTime salesEndDate;

//    @EqualsAndHashCode.Include
    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizer_id")
    private User organizer;

    @ManyToMany(mappedBy = "attendingEvents")
    private List<User> attendees=new ArrayList<>();

    @ManyToMany(mappedBy = "staffingEvents")
    private List<User> staff=new ArrayList<>();

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TicketType> ticketTypes = new ArrayList<>();

    @EqualsAndHashCode.Include
    @CreatedDate
    @Column(name = "created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;

//    @EqualsAndHashCode.Include
    @LastModifiedDate
    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;
}
