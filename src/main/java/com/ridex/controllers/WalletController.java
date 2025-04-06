package com.ridex.controllers;

import com.ridex.dto.ApiResponse;
import com.ridex.dto.WalletDashboardDto;
import com.ridex.entity.UserEntity;
import com.ridex.entity.WalletTransactionEntity;
import com.ridex.repository.UserRepository;
import com.ridex.services.WalletService;
import com.ridex.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/dashboard")
    public ApiResponse getWalletDashboard(HttpServletRequest request) {
        int userId = jwtUtil.getUserIdFromRequest(request);
        UserEntity driver = userRepo.getById(userId);

        double balance = walletService.getBalance(driver);
        List<WalletTransactionEntity> transactions = walletService.getRecentTransactions(driver);
        WalletDashboardDto dto = new WalletDashboardDto(balance, transactions);
        return ApiResponse.success("Wallet dashboard loaded", dto);
    }
}
