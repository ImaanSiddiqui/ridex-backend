package com.ridex.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class DriverRideFullInfoDto {
	
	    private int rideId;
	    private String fromLocation;
	    private String toLocation;
	    private LocalDateTime departureTime;
	    private int totalSeats;
	    private int availableSeats;
	    private double pricePerSeat;
	    private int totalBookedSeats;
	    private double totalCollected;

	    private List<PassengerInfo> passengers;

	    @Data
	    public static class PassengerInfo {
	        private String name;
	        private int seatsBooked;
	        private String paymentType;
	        private String paymentStatus;
	        private double amountPaid;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public int getSeatsBooked() {
				return seatsBooked;
			}
			public void setSeatsBooked(int seatsBooked) {
				this.seatsBooked = seatsBooked;
			}
			public String getPaymentType() {
				return paymentType;
			}
			public void setPaymentType(String paymentType) {
				this.paymentType = paymentType;
			}
			public String getPaymentStatus() {
				return paymentStatus;
			}
			public void setPaymentStatus(String paymentStatus) {
				this.paymentStatus = paymentStatus;
			}
			public double getAmountPaid() {
				return amountPaid;
			}
			public void setAmountPaid(double amountPaid) {
				this.amountPaid = amountPaid;
			}
	        
	        
	        
	        
	        
	    
	}

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

		public int getTotalSeats() {
			return totalSeats;
		}

		public void setTotalSeats(int totalSeats) {
			this.totalSeats = totalSeats;
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

		public int getTotalBookedSeats() {
			return totalBookedSeats;
		}

		public void setTotalBookedSeats(int totalBookedSeats) {
			this.totalBookedSeats = totalBookedSeats;
		}

		public double getTotalCollected() {
			return totalCollected;
		}

		public void setTotalCollected(double totalCollected) {
			this.totalCollected = totalCollected;
		}

		public List<PassengerInfo> getPassengers() {
			return passengers;
		}

		public void setPassengers(List<PassengerInfo> passengers) {
			this.passengers = passengers;
		}
	    
	    
	    
	    


}
