package com.ridex.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking; // ✅ Linked to Booking

    private double amountPaid; // ✅ Total Amount

    private LocalDateTime paymentTime; // ✅ Timestamp

    private String paymentMethod; // ✅ UPI, Card, etc.
}
