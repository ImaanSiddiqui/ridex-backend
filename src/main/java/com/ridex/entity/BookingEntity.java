package com.ridex.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ride_id", nullable = false)
    private RideEntity ride; // ✅ Linked to Ride

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private UserEntity rider; // ✅ Linked to Rider

    private int seatsBooked; // ✅ Number of seats booked

    private boolean isPaid = false; // ✅ Payment status
}
