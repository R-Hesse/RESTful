package org.ac.cst8277.hesse.ryan;

public class Subscriber {
	
	private String userId;
	private String subscriberId;
	private String name;
	
	Subscriber(String userId, String subscriberId, String name) {
		this.userId = userId;
		this.subscriberId = subscriberId;
		this.name = name;
	}
	
	public Subscriber() {
		// TODO Auto-generated constructor stub
	}

	public void searchPublisher() {
		
	}
	
	public void searchMessage() {
		
	}

	public void subscribe() {
		
	}
	
	public void unsubscribe() {
		
	}
	
	public void setUserId(String userId) {	
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	
	public void setSubscriberId(String subscriberId) {	
		this.subscriberId = subscriberId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	
	public void setName(String name) {	
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
}
