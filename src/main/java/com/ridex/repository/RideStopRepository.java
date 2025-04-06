package com.ridex.repository;

import com.ridex.entity.RideStopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideStopRepository extends JpaRepository<RideStopEntity, Integer> {
    List<RideStopEntity> findByRideIdOrderByStopOrderAsc(int rideId);
    List<RideStopEntity> findByRideId(int rideId);
}
