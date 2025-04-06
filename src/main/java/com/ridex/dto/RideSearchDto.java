package com.ridex.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RideSearchDto {
    private int rideId;
    private String fromLocation;
    private String toLocation;
    private LocalDateTime departureTime;
    private int availableSeats;
    private double pricePerSeat;
    private String driverName;
    private String driverImageUrl;
    private String matchedStopMessage;
    
    
    
    
    
	public int getRideId() {
		return rideId;
	}
	public void setRideId(int rideId) {
		this.rideId = rideId;
	}
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
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public double getPricePerSeat() {
		return pricePerSeat;
	}
	public void setPricePerSeat(double pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverImageUrl() {
		return driverImageUrl;
	}
	public void setDriverImageUrl(String driverImageUrl) {
		this.driverImageUrl = driverImageUrl;
	}
	public String getMatchedStopMessage() {
		return matchedStopMessage;
	}
	public void setMatchedStopMessage(String matchedStopMessage) {
		this.matchedStopMessage = matchedStopMessage;
	}
	
	
    
    
    
    
    
    // no mobile number here

    // getters and setters
}
