package com.ridex.services;

import com.ridex.entity.TopUpRequest;
import com.ridex.entity.UserEntity;
import com.ridex.repository.TopUpRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopUpService {

    @Autowired
    private TopUpRequestRepository topUpRepo;

    @Autowired
    private WalletService walletService;

    public TopUpRequest createTopUp(UserEntity driver, double amount, String screenshotUrl) {
        TopUpRequest request = new TopUpRequest();
        request.setDriver(driver);
        request.setAmount(amount);
        request.setScreenshotUrl(screenshotUrl);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());
        return topUpRepo.save(request);
    }

    public TopUpRequest approveTopUp(int requestId, String remarks) {
        TopUpRequest request = topUpRepo.findById(requestId).orElse(null);
        if (request != null && request.getStatus().equals("PENDING")) {
            request.setStatus("APPROVED");
            request.setRemarks(remarks);
            request.setUpdatedAt(LocalDateTime.now());
            topUpRepo.save(request);

            // Add to wallet
            walletService.credit(request.getDriver(), request.getAmount(), "Top-up approved");
        }
        return request;
    }

    public TopUpRequest rejectTopUp(int requestId, String remarks) {
        TopUpRequest request = topUpRepo.findById(requestId).orElse(null);
        if (request != null && request.getStatus().equals("PENDING")) {
            request.setStatus("REJECTED");
            request.setRemarks(remarks);
            request.setUpdatedAt(LocalDateTime.now());
            topUpRepo.save(request);
        }
        return request;
    }

    public List<TopUpRequest> getRequestsByDriver(UserEntity driver) {
        return topUpRepo.findByDriver(driver);
    }

    public List<TopUpRequest> getPendingRequests() {
        return topUpRepo.findByStatus("PENDING");
    }
}
