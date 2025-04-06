package com.ridex.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ride_stops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideStopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ride_id", nullable = false)
    private RideEntity ride;

    private String stopName;

    private int stopOrder; // To preserve order (1: Pune, 2: Lonavala, 3: Mumbai)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RideEntity getRide() {
		return ride;
	}

	public void setRide(RideEntity ride) {
		this.ride = ride;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public int getStopOrder() {
		return stopOrder;
	}

	public void setStopOrder(int stopOrder) {
		this.stopOrder = stopOrder;
	}
    
    
    
}
