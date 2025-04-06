package com.ridex.controllers;

import com.ridex.dto.ApiResponse;
import com.ridex.entity.TopUpRequest;
import com.ridex.entity.UserEntity;
import com.ridex.repository.UserRepository;
import com.ridex.services.TopUpService;
import com.ridex.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/topup")
public class TopUpController {

    @Autowired
    private TopUpService topUpService;

    
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JwtUtil jwtUtil;

    // 🚗 Driver Uploads Top-Up Request with Screenshot
    @PostMapping("/request")
    public ApiResponse requestTopUp(
            @RequestParam double amount,
            @RequestParam MultipartFile screenshot,
            HttpServletRequest req) {
    	int userId = jwtUtil.getUserIdFromRequest(req);
    	UserEntity authUser = userRepo.getById(userId);
    	
    	

        UserEntity driver = authUser;// your session logic here

        // Upload image logic (mocked path here)
        String screenshotUrl = "/uploads/" + screenshot.getOriginalFilename(); // replace with actual upload logic

        TopUpRequest request = topUpService.createTopUp(driver, amount, screenshotUrl);
        return ApiResponse.success("Top-up request submitted", request);
    }

    // 👀 Driver Views All His Requests
    @GetMapping("/my")
    public ApiResponse getMyTopUps(HttpServletRequest request) {
    	
    	int userId = jwtUtil.getUserIdFromRequest(request);
    	UserEntity authUser = userRepo.getById(userId);
    	
    	

        UserEntity driver = authUser;// your session logic here

        List<TopUpRequest> list = topUpService.getRequestsByDriver(driver);
        return ApiResponse.success("Fetched", list);
    }

    // 🛠️ Admin: View Pending Top-Ups
    @GetMapping("/pending")
    public ApiResponse getPending() {
        return ApiResponse.success("Pending requests", topUpService.getPendingRequests());
    }

    // ✅ Admin Approves Top-Up
    @PutMapping("/approve/{id}")
    public ApiResponse approveTopUp(@PathVariable int id, @RequestParam(required = false) String remarks) {
        return ApiResponse.success("Approved", topUpService.approveTopUp(id, remarks));
    }

    // ❌ Admin Rejects Top-Up
    @PutMapping("/reject/{id}")
    public ApiResponse rejectTopUp(@PathVariable int id, @RequestParam String remarks) {
        return ApiResponse.success("Rejected", topUpService.rejectTopUp(id, remarks));
    }
}
