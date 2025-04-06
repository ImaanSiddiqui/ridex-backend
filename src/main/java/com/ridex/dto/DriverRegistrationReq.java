package com.ridex.dto;

import lombok.Data;

@Data
public class DriverRegistrationReq {
	

	
	    private int userId;
	    private String drivingLicenseNumber;
	    private String licenseImageUrl;     // optional
	    private String carModel;
	    private String carNumberPlate;
	    private String carPhotoUrl;         // optional
	    private String aadharNumber;        // optional
	    private String driverPhotoUrl;      // ✅ Required
	    
	    
	    
	    
	    
	    
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getDrivingLicenseNumber() {
			return drivingLicenseNumber;
		}
		public void setDrivingLicenseNumber(String drivingLicenseNumber) {
			this.drivingLicenseNumber = drivingLicenseNumber;
		}
		public String getLicenseImageUrl() {
			return licenseImageUrl;
		}
		public void setLicenseImageUrl(String licenseImageUrl) {
			this.licenseImageUrl = licenseImageUrl;
		}
		public String getCarModel() {
			return carModel;
		}
		public void setCarModel(String carModel) {
			this.carModel = carModel;
		}
		public String getCarNumberPlate() {
			return carNumberPlate;
		}
		public void setCarNumberPlate(String carNumberPlate) {
			this.carNumberPlate = carNumberPlate;
		}
		public String getCarPhotoUrl() {
			return carPhotoUrl;
		}
		public void setCarPhotoUrl(String carPhotoUrl) {
			this.carPhotoUrl = carPhotoUrl;
		}
		public String getAadharNumber() {
			return aadharNumber;
		}
		public void setAadharNumber(String aadharNumber) {
			this.aadharNumber = aadharNumber;
		}
		public String getDriverPhotoUrl() {
			return driverPhotoUrl;
		}
		public void setDriverPhotoUrl(String driverPhotoUrl) {
			this.driverPhotoUrl = driverPhotoUrl;
		}
	

	    
	    
	    
	    
}

