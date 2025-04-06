package com.ridex.controllers;

import com.ridex.dto.DriverRideFullInfoDto;
import com.ridex.dto.RideCreateReq;
import com.ridex.dto.RideJoinReq;
import com.ridex.dto.RideSearchDto;
import com.ridex.dto.RideSearchReq;
import com.ridex.entity.RideEntity;
import com.ridex.services.RideService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

	
	@Autowired
    private RideService rideService;

	@PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PostMapping("/offer")
    public ResponseEntity<?> offerRide(@RequestBody RideCreateReq req) {
        rideService.offerRide(req);
        return ResponseEntity.ok("Ride offered successfully");
    }
    
    
	@PreAuthorize("hasAuthority('ROLE_RIDER')")
    @PostMapping("/join")
    public ResponseEntity<?> joinRide(@RequestBody RideJoinReq req) {
        rideService.joinRide(req);
        return ResponseEntity.ok("Ride joined successfully");
    }
    
	@PreAuthorize("hasAuthority('ROLE_RIDER')")
    @PostMapping("/search")
    public ResponseEntity<?> searchRides(@RequestBody RideSearchReq req) {
        List<RideSearchDto> results = rideService.searchRides(req);
        return ResponseEntity.ok(results);
    }
    
	@PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping("/driver/bookings/{driverId}")
    public ResponseEntity<?> getDriverRideBookings(@PathVariable int driverId) {
        return ResponseEntity.ok(rideService.getDriverBookings(driverId));
    }
    
    
	@PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping("/complete/{rideId}")
    public ResponseEntity<?> completeRide(@PathVariable int rideId) {
        rideService.markRideAsCompleted(rideId);
        return ResponseEntity.ok("Ride marked as completed.");
    }
    
	@PreAuthorize("hasAuthority('ROLE_RIDER')")
    @GetMapping("/joined/{riderId}")
    public ResponseEntity<?> getJoinedRides(@PathVariable int riderId) {
        return ResponseEntity.ok(rideService.getJoinedRides(riderId));
    }
	
	
	
	@GetMapping("/driver/ride-details/{rideId}")
	public ResponseEntity<DriverRideFullInfoDto> getRideDetails(@PathVariable int rideId) {
	    return ResponseEntity.ok(rideService.getRideDetailsForDriver(rideId));
	}
}
