package com.ridex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ridex.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
	
	Optional<DriverEntity> findByUserId(int userId);	
	
	

}
