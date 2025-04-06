package com.ridex.utils;


import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ridex.exceptions.FirebaseAuthExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FirebaseAuthServiceUtil {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthServiceUtil.class); // Logger for better tracking
    
    
    
    // ✅ Verify Firebase ID Token and get phone number from claims
    public String verifyFirebaseToken(String firebaseToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
            System.out.println(decodedToken);

            // ✅ Extract phone number from claims
            Object phoneNumberObj = decodedToken.getClaims().get("phone_number");
            
            if (phoneNumberObj != null) {
                log.debug("Firebase Token - Phone number: {}", phoneNumberObj.toString());
                return phoneNumberObj.toString();  // Convert to String
            } else {
                log.warn("Phone number not found in Firebase token!");
                throw new RuntimeException("Phone number not found in Firebase token!");
            }

        } catch (FirebaseAuthException e) {
            log.error("Firebase Authentication failed: {}", e.getMessage());
            throw new RuntimeException("Invalid Firebase Token! " + e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error during token verification: " + e.getMessage());
        }
    }
    
    
    
    // ✅ Verify OTP via Firebase ID Token
    public String verifyOtp(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken.getUid();
        } catch (FirebaseAuthException e) {
            log.error("OTP verification failed: {}", e.getMessage());
            throw new FirebaseAuthExceptions("Error verifying OTP: " + e.getMessage());
        }
    }
    
    
    

    // ✅ Send OTP to the user's phone via Firebase
    public String sendOtp(String phone) throws FirebaseAuthException {
        try {
            String customToken = FirebaseAuth.getInstance().createCustomToken(phone);
            return customToken;
        } catch (FirebaseAuthException e) {
        	log.error("OTP sending failed: {}", e.getMessage());
            throw new FirebaseAuthExceptions("Error verifying OTP: " + e.getMessage());
        }
    }
    
    
    

    // ✅ Send a password reset email
//    public String sendPasswordResetEmail(String email) {
//        try {
//            FirebaseAuth.getInstance().generatePasswordResetLink(email);
//            log.info("Password reset link sent to email: {}", email);
//            return "Password reset link sent!";
//        } catch (FirebaseAuthException e) {
//            log.error("Error sending password reset link to email: {}", email);
//            return "Error sending reset link: " + e.getMessage();
//        } catch (Exception e) {
//            log.error("Unexpected error during password reset link generation: {}", e.getMessage());
//            return "Error during password reset: " + e.getMessage();
//        }
//    }
}

