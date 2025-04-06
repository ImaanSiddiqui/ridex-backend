package com.ridex.repository;

import com.ridex.entity.RideJoinEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RideJoinRepository extends JpaRepository<RideJoinEntity, Integer> {
	
	int countByRideId(int rideId);
	
	List<RideJoinEntity> findByUserId(int riderId );
	
	List<RideJoinEntity> findByRideId(int rideId );

}
