package com.ridex.repository;

import com.ridex.entity.DriverWalletEntity;
import com.ridex.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverWalletRepository extends JpaRepository<DriverWalletEntity, Integer> {
    DriverWalletEntity findByDriver(UserEntity driver);
}
