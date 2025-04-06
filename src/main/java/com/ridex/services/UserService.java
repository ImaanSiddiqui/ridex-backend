package com.ridex.services;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.ridex.dto.RoleSwitchReq;
import com.ridex.dto.RoleSwitchRes;
import com.ridex.entity.DriverEntity;
import com.ridex.entity.Role;
import com.ridex.entity.UserEntity;
import com.ridex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ridex.utils.JwtUtil;


@Service
public class UserService {

	
	@Autowired
	private JwtUtil JwtUtil;
    @Autowired
    private UserRepository userRepository;
    
    
    
    public String verifyUser(String firebaseToken) throws Exception {
        // ✅ Step 1: Verify Firebase Token
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        String firebaseUid = decodedToken.getUid();

        // 🔍 Step 2: Get Phone Number from Firebase
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(firebaseUid);
        String phoneNumber = userRecord.getPhoneNumber();

        // 🔍 Step 3: Check if User Exists in Database
        Optional<UserEntity> userExists = userRepository.findByPhoneNumber(phoneNumber);

        if (userExists.isPresent()) {
            // ✅ If User Exists → Return JWT
            return JwtUtil.generateToken(userExists.get());
        }

        // ❌ If User Not Found → Return null (handled in controller)
        return null;
    }

    // 🚀 Register a New User
    public String registerUser(String firebaseToken, String name, String gender, int age) throws Exception {
        // ✅ Step 1: Verify Firebase Token
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        String firebaseUid = decodedToken.getUid();

        // 🔍 Step 2: Get Phone Number from Firebase
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(firebaseUid);
        String phoneNumber = userRecord.getPhoneNumber();

        // 🔍 Step 3: Check if User Already Exists
        Optional<UserEntity> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if (existingUser.isPresent()) {
            throw new Exception("User already exists.");
        }

        // 🚀 Step 4: Create New User
        UserEntity newUser = new UserEntity();
        newUser.setPhoneNumber(phoneNumber);
        newUser.setFullName(name);
        newUser.setGender(gender);
        newUser.setAge(age);

        // 💾 Save User in Database
        userRepository.save(newUser);

        // 🔑 Step 5: Generate and Return JWT Token
        return JwtUtil.generateToken(newUser);
    }
    
    public Optional<UserEntity> getRiderById(int id) {
    	 
    	      return userRepository.findById(id);

    		

  }
    
    
    public RoleSwitchRes switchUserRoleAndGenerateNewToken(int userId, Role newRole) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOptional.get();
        user.setRole(newRole);
        user.setDriver(newRole == Role.DRIVER);
        userRepository.save(user);

        // Generate token (always generate)
        String newToken = JwtUtil.generateToken(user);

        if (newRole == Role.DRIVER) {
            DriverEntity driverEntity = user.getDriverEntity();
            if (driverEntity == null || driverEntity.getDrivingLicenseNumber()==null) {
                // Return generated token but inform UI to navigate to driver profile form
                return new RoleSwitchRes(newToken, true);
            }
        }

        // All good, return token and no driver info needed
        return new RoleSwitchRes(newToken, false);
    }
    // Add new Rider
//    public String loginOrRegisterRider(String firebaseToken, String name, String gender) throws Exception {
//        // 🔥 Step 1: Verify Firebase OTP Token
//        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
//        String firebaseUid = decodedToken.getUid();
//
//        // 🔍 Step 2: Get Rider's Phone Number
//        UserRecord userRecord = FirebaseAuth.getInstance().getUser(firebaseUid);
//        String phoneNumber = userRecord.getPhoneNumber();
//
//        // 🔍 Step 3: Check if Rider Already Exists
//        Optional<UserEntity> existingRider = userRepository.findByPhoneNumber(phoneNumber);
//
//        if (existingRider.isPresent()) {
//            // ✅ Rider exists, just return JWT
//            return JwtUtil.generateToken(phoneNumber);
//        }
//
//        // 🚀 Step 4: New user → Save in DB
//        UserEntity newRider = new UserEntity();
//        newRider.setPhoneNumber(phoneNumber);
//        newRider.setName(name);
//        newRider.setGender(gender);
//        riderRepository.save(newRider);
//
//        // 🔑 Step 5: Generate JWT Token
//        return JwtUtil.generateToken(phoneNumber);
//    }
//    
//    
//    
//    public UserEntity save(UserEntity rider) {
//        return userRepository.save(rider);
//    }
//    
//    
    public Optional<UserEntity> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
//    
//
//    // Get Rider by ID
//    public Optional<UserEntity> getRiderById(int id) {
//        return userRepository.findById(id);
//    }
//
//    // Get all Riders
//    public List<UserEntity> getAllRiders() {
//        return userRepository.findAll();
//    }
//
//    // Get Rider by Phone Number
//    public Optional<UserEntity> getRiderByPhoneNumber(String phoneNumber) {
//        return userRepository.findByPhoneNumber(phoneNumber);
//    }
//
//    // Update Rider
////    public RiderEntity updateRider(int id, RiderEntity updatedRider) {
////        return riderRepository.findById(id).map(rider -> {
////            rider.setName(updatedRider.getName());
////            rider.setPhoneNumber(updatedRider.getPhoneNumber());
////            rider.setGender(updatedRider.getGender());
////            return riderRepository.save(rider);
////        }).orElse(null);
////    }
//
//    // Delete Rider
    public void deleteRider(String token) {
    	int userId = JwtUtil.extractUserId(token);
        userRepository.deleteById(userId);
    }
//    
//    
//    public UserEntity updateRider(int id, UserEntity updatedRider) {
//        return userRepository.findById(id).map(rider -> {
//            rider.getFullName(updatedRider.getName());
//            rider.setGender(updatedRider.getGender());
//            return riderRepository.save(rider);
//        }).orElseThrow();
//    }
    
    
//    
//  public List<RidePublicDto> getRideHistory(int userId) {
//  return rideRepo.findById(userId).stream()
//          .map(this::convertToRidePublicDto)
//          .collect(Collectors.toList());
//}
    
    
}

