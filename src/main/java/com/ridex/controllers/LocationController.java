package com.ridex.controllers;

import com.ridex.dto.LocationUpdateReq;
import com.ridex.entity.LocationEntity;
import com.ridex.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(@RequestBody LocationUpdateReq req) {
        locationService.updateLocation(req);
        return ResponseEntity.ok("Location updated successfully");
    }
    
    
    
    @GetMapping("/{rideId}")
    public ResponseEntity<?> getLiveLocation(@PathVariable int rideId) {
        LocationEntity location = locationService.getLetestLocation(rideId);
        if (location == null) {
            return ResponseEntity.status(404).body("Location not found for this ride");
        }
        return ResponseEntity.ok(location);
    }
}
