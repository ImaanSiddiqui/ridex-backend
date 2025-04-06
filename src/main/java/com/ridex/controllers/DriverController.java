package com.ridex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ridex.dto.DriverRegistrationReq;
import com.ridex.services.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

	
	
	@Autowired
    private  DriverService driverService;  // ✅ No @Autowired needed

    @PostMapping("/register")
    public ResponseEntity<?> registerAsDriver(@RequestBody DriverRegistrationReq req) {
        driverService.registerDriver(req);
        return ResponseEntity.ok().body("Driver registered successfully.");
    }
}
