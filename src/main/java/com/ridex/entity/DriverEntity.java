package com.ridex.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "driver_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // ✅ Linked to User Table

    @Column(unique = true, nullable = false)
    private String drivingLicenseNumber;  // ✅ Unique License Number

    private String licenseImageUrl;  // ✅ Image of License
    
    private String DriverImageUrl;

    private String carModel;  // ✅ Car Model Name

    @Column(unique = true, nullable = false)
    private String carNumberPlate;  // ✅ Unique Car Plate

    private String carPhotoUrl;  // ✅ Image of Car

    private String aadharNumber;  // ✅ Optional for extra verification

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

	public String getDriverImageUrl() {
		return DriverImageUrl;
	}

	public void setDriverImageUrl(String driverImageUrl) {
		DriverImageUrl = driverImageUrl;
	}
	
	
	
    
    
    
    
    
    
    
}
