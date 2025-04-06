package com.ridex.config;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ridex.repository.UserRepository;
import com.ridex.entity.Role;
import com.ridex.entity.UserEntity;


public class UserServiceRole implements UserDetailsService {
	
	private final UserRepository userRepo;
	
	
	public UserServiceRole(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
	    System.out.println("🔍 Searching user in DB: " + phone);
	    
	    Optional<UserEntity> userOptional = userRepo.findByPhoneNumber(phone);

	    if (userOptional.isEmpty()) {
	        System.out.println("❌ User not found in database: " + phone);
	        throw new UsernameNotFoundException("User Not Found: " + phone);
	    }

	    UserEntity user = userOptional.get();
	    
	    // Validate phone number
	    if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
	        System.out.println("⚠️ Phone number is null or empty!");
	        throw new IllegalArgumentException("User phone number cannot be null or empty");
	    }

	    // Validate role
	    if (user.getRole() == null) {
	        System.out.println("⚠️ User role is NULL! Assigning default role: RIDER");
	        user.setRole(Role.RIDER);  // Assign a default role
	    }

	    String role = "ROLE_" + user.getRole().name();
	    System.out.println("✅ Assigned Role: " + role);

	    return org.springframework.security.core.userdetails.User
	        .withUsername(user.getPhoneNumber())
	        .authorities(List.of(new SimpleGrantedAuthority(role)))
	        .password("") // Spring Security requires a password, but it can be empty if not used
	        .build();
	}


	    
}



