package com.ridex.dto;

import lombok.AllArgsConstructor;

public class RoleSwitchRes {
	
	
	// File: RoleSwitchResponse.java

	    private String token;
	    private boolean driverInfoRequired;
	    
	    
	   public RoleSwitchRes(String token, boolean driverInfoRequired){
	    	this.token = token;
	    	this.driverInfoRequired = driverInfoRequired;
	    }

	    
	 

	    public String getToken() {
	        return token;
	    }

	    public boolean isDriverInfoRequired() {
	        return driverInfoRequired;
	    }
	


}
