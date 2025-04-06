package com.ridex.repository;

import com.ridex.entity.TopUpRequest;
import com.ridex.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopUpRequestRepository extends JpaRepository<TopUpRequest, Integer> {
    List<TopUpRequest> findByDriver(UserEntity driver);
    List<TopUpRequest> findByStatus(String status);
}
