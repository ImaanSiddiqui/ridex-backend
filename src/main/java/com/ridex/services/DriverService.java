package com.ridex.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ridex.dto.DriverRegistrationReq;
import com.ridex.entity.DriverEntity;
import com.ridex.entity.UserEntity;
import com.ridex.repository.DriverRepository;
import com.ridex.repository.UserRepository;



@Service
public class DriverService {
	
	 @Autowired
	 private DriverRepository driverRepo ;
	 
	 @Autowired
	 private UserRepository userRepo;

	
	 public void registerDriver(DriverRegistrationReq req) {
	        UserEntity user = userRepo.findById(req.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (user.getDriverEntity() != null) {
	            throw new RuntimeException("Driver already registered.");
	        }

	        if (req.getDriverPhotoUrl() == null || req.getDriverPhotoUrl().isEmpty()) {
	            throw new RuntimeException("Driver photo is required.");
	        }

	        DriverEntity driver = new DriverEntity();
	        driver.setUser(user);
	        driver.setDrivingLicenseNumber(req.getDrivingLicenseNumber());
	        driver.setLicenseImageUrl(req.getLicenseImageUrl());
	        driver.setCarModel(req.getCarModel());
	        driver.setCarNumberPlate(req.getCarNumberPlate());
	        driver.setCarPhotoUrl(req.getCarPhotoUrl());
	        driver.setAadharNumber(req.getAadharNumber());
	        driver.setDriverImageUrl(req.getDriverPhotoUrl()); // ✅ New field

	        driverRepo.save(driver);

	        user.setDriverEntity(driver);
	        userRepo.save(user);
	    }
}
