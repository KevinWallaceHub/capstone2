package com.techelevator.tenmo.model;

public class AuthenticatedUser {
	//class where the token is stored! Uses this to handle login in app
	private String token;
	private User user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
