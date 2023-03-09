package org.ac.cst8277.hesse.ryan;

public class Publisher {
	
	private int userId;
	private int publisherId;
	private String name;
	
	Publisher(int userId, int publisherId, String name) {
		this.userId = userId;
		this.publisherId = publisherId;
		this.name = name;
	}
	
	public void publishMessage() {
		
	}
	
	public void updateMessage() {
		
	}
	
	public void deleteMessage() {
		
	}
	
	public void viewSubscribers() {
		
	}
	
	public void searchMessage() {
		
	}
	
	public void setUserId(int userId) {	
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}
	
	public void setPublisherId(int publisherId) {	
		this.publisherId = publisherId;
	}
	public int getPublisherId() {
		return publisherId;
	}
	
	public void setName(String name) {	
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
