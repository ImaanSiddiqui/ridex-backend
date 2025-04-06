package com.ridex.repository;

import com.ridex.entity.WalletTransactionEntity;
import com.ridex.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransactionEntity, Integer> {
    List<WalletTransactionEntity> findByDriver(UserEntity driver);
    List<WalletTransactionEntity> findTop5ByDriverOrderByTransactionTimeDesc(UserEntity driver);

}
