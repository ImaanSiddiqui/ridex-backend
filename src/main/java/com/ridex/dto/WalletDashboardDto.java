package com.ridex.dto;

import com.ridex.entity.WalletTransactionEntity;
import java.util.List;

public class WalletDashboardDto {
    private double balance;
    private List<WalletTransactionEntity> recentTransactions;

    public WalletDashboardDto(double balance, List<WalletTransactionEntity> recentTransactions) {
        this.balance = balance;
        this.recentTransactions = recentTransactions;
    }

    // Getters and setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<WalletTransactionEntity> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<WalletTransactionEntity> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
