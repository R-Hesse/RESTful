package org.ac.cst8277.hesse.ryan;


public class User {

	private String userId;
	private String name;
	private String email;
	private String password;
	private String createdOn;
	private String lastVisit;
	private String token;
	private String roleId;
	private String publisherId;
	private String subscriberId;
	private String dogsName;
	private String messageContents;


	User(String userId, String name, String email, String password, String createdOn, String lastVisit, String token) {
				
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.createdOn = createdOn;
		this.lastVisit = lastVisit;
		this.token = token;
	}
	
	
	
	public void loginUser() {
		
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}
	
	public User ( ) {
		
	}
	
	public void setUserId(String userId) {	
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	
	public void setName(String name) {	
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setEmail(String email) {	
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
	public void setPassword(String password) {	
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	public void setCreatedOn(String createdOn) {	
		this.createdOn = createdOn;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	
	public void setLastVisit(String lastVisit) {	
		this.lastVisit = lastVisit;
	}
	public String getLastVisit() {
		return lastVisit;
	}
	
	public void setToken(String token) {	
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	
	public void setRole(String roleId) {	
		this.roleId = roleId;
	}
	public String getRole() {
		return roleId;
	}
	
	public void setPub(String publisherId) {	
		this.publisherId = publisherId;
	}
	public String getPub() {
		return publisherId;
	}
	
	public void setSub(String subscriberId) {	
		this.subscriberId = subscriberId;
	}
	public String getSub() {
		return subscriberId;
	}
	
	public void setDog(String dogsName) {	
		this.dogsName = dogsName;
	}
	public String getDog() {
		return dogsName;
	}
	
	public void setComment(String messageContents) {	
		this.messageContents = messageContents;
	}
	public String getComment() {
		return messageContents;
	}
		
}
