package com.ridex.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private UserEntity driver; // ✅ Driver being reviewed

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private UserEntity rider; // ✅ Rider who gives review

    private int rating; // ✅ Stars (1-5)

    private String feedback; // ✅ Optional comments
}
