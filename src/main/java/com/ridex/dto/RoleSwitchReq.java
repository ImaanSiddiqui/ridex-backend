package com.ridex.dto;

import com.ridex.entity.Role;

import lombok.Data;

@Data
public class RoleSwitchReq {
	private int userId;
	private Role newRole;
	
	
	
	
	public void setUserId(int id) {
		this.userId = id;
	}
	
	
	public int getUserId() {
		return userId;
	}


	public Role getNewRole() {
		return newRole;
	}


	public void setNewRole(Role newRole) {
		this.newRole = newRole;
	}
	
	

}
