package com.ridex.services;

import com.ridex.dto.LocationUpdateReq;
import com.ridex.entity.LocationEntity;
import com.ridex.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepo;

    public void updateLocation(LocationUpdateReq req) {
        LocationEntity loc = new LocationEntity();
        loc.setRideId(req.getRideId());
        loc.setDriverId(req.getDriverId());
        loc.setLatitude(req.getLatitude());
        loc.setLongitude(req.getLongitude());
        locationRepo.save(loc);
    }
    
    
    
    
    public LocationEntity getLetestLocation(int rideId) {
        return locationRepo.findTopByRideIdOrderByUpdatedAtDesc(rideId);
    }
}
