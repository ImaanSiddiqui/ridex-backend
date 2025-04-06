package com.ridex.services;

import com.ridex.config.CommissionConfig;
import com.ridex.entity.DriverWalletEntity;
import com.ridex.entity.UserEntity;
import com.ridex.entity.WalletTransactionEntity;
import com.ridex.repository.DriverWalletRepository;
import com.ridex.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private DriverWalletRepository walletRepo;

    @Autowired
    private WalletTransactionRepository transactionRepo;

    // ✅ CREDIT Method (Top-Up or Reward)
    public void credit(UserEntity driver, double amount, String remarks) {
        DriverWalletEntity wallet = walletRepo.findByDriver(driver);
        if (wallet == null) {
            // Create wallet if not found
            wallet = new DriverWalletEntity();
            wallet.setDriver(driver);
            wallet.setBalance(0.0);
            wallet.setUpdatedAt(LocalDateTime.now());
        }

        wallet.setBalance(wallet.getBalance() + amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepo.save(wallet);

        WalletTransactionEntity tx = new WalletTransactionEntity();
        tx.setDriver(driver);
        tx.setAmount(amount);
        tx.setType("TOP_UP");
        tx.setRemarks(remarks);
        tx.setTransactionTime(LocalDateTime.now());
        transactionRepo.save(tx);
    }

    // ✅ DEBIT Method (Commission)
    public void deductCommission(UserEntity driver, int seatsBooked, String rideRef) {
        double commission = seatsBooked * CommissionConfig.COMMISSION_PER_SEAT;

        DriverWalletEntity wallet = walletRepo.findByDriver(driver);
        if (wallet == null || wallet.getBalance() < commission) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance() - commission);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepo.save(wallet);

        WalletTransactionEntity txn = new WalletTransactionEntity();
        txn.setDriver(driver);
        txn.setAmount(-commission);
        txn.setType("DEBIT");
        txn.setRemarks("Commission for ride: " + rideRef);
        txn.setTransactionTime(LocalDateTime.now());
        transactionRepo.save(txn);
    }

    // ✅ GET BALANCE
    public double getBalance(UserEntity driver) {
        DriverWalletEntity wallet = walletRepo.findByDriver(driver);
        return wallet != null ? wallet.getBalance() : 0.0;
    }
    
    
    
    public List<WalletTransactionEntity> getRecentTransactions(UserEntity driver) {
        return transactionRepo.findTop5ByDriverOrderByTransactionTimeDesc(driver);
    }
}
