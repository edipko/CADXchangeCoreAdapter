package com.spotonresonse.httputils;

public class UserAuthenticationObject {

	private String username;
	private String password;
	
	public UserAuthenticationObject(String _username, String _password) {
		username = _username;
		password = _password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
