package com.ridex.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideSearchReq {
    private String fromLocation;
    private String toLocation;
    private LocalDateTime preferredDepartureTime; // optional
    private Integer seatsRequired; // optional
    
    
    
    
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public LocalDateTime getPreferredDepartureTime() {
		return preferredDepartureTime;
	}
	public void setPreferredDepartureTime(LocalDateTime preferredDepartureTime) {
		this.preferredDepartureTime = preferredDepartureTime;
	}
	public Integer getSeatsRequired() {
		return seatsRequired;
	}
	public void setSeatsRequired(Integer seatsRequired) {
		this.seatsRequired = seatsRequired;
	}
	
	
	
	
    
    
    
}
