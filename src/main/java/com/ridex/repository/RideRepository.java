package com.ridex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ridex.entity.RideEntity;

public interface RideRepository extends JpaRepository<RideEntity, Integer>,JpaSpecificationExecutor<RideEntity> {
	Optional<RideEntity> findById(int userId);
	List<RideEntity> findByDriverId(int driverId);


}
