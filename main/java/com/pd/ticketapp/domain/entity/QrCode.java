package com.pd.ticketapp.domain.entity;

import com.pd.ticketapp.domain.enums.QrCodeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="qr_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class QrCode {

    @EqualsAndHashCode.Include
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

//    @EqualsAndHashCode.Include
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private QrCodeStatus status;

//    @EqualsAndHashCode.Include
    @Column(name = "value",nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @EqualsAndHashCode.Include
    @CreatedDate
    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

//    @EqualsAndHashCode.Include
    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

}
