package com.ridex.repository;

import com.ridex.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity,Integer> {
    LocationEntity findTopByRideIdOrderByUpdatedAtDesc(int rideId); // For getting last known location
}
