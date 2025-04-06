package com.ridex.controllers;

import com.ridex.dto.RoleSwitchReq;
import com.ridex.dto.RoleSwitchRes;
import com.ridex.entity.UserEntity;
import com.ridex.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody Map<String, String> requestBody) {
        String firebaseToken = requestBody.get("firebaseToken");

        try {
            String jwtToken = userService.verifyUser(firebaseToken);

            if (jwtToken != null) {
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "User not found. Please register."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    
    @PostMapping("/switch-role")
    public ResponseEntity<RoleSwitchRes> switchRole(@RequestBody RoleSwitchReq request, HttpServletResponse response) {
        RoleSwitchRes result = userService.switchUserRoleAndGenerateNewToken(request.getUserId(), request.getNewRole());

        if (result.getToken() != null) {
            response.setHeader("Authorization", "Bearer " + result.getToken());
        }

        return ResponseEntity.ok(result);
    }
    
    
    
    

    // 🚀 **Register User API**
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> requestBody) {
        String firebaseToken = requestBody.get("firebaseToken");
        String name = requestBody.get("name");
        String gender = requestBody.get("gender");
        int age = Integer.parseInt(requestBody.get("age"));

        try {
            String jwtToken = userService.registerUser(firebaseToken, name, gender, age);
            return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRiderById(@PathVariable int id) {
        Optional<UserEntity> user = userService.getRiderById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,@RequestParam String token) {
        userService.deleteRider(token);
        return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
    }
}






