package org.ac.cst8277.hesse.ryan;

public class Subscription {

	private String name;
	private String publisherId;
	private String subscriberId;
	
	Subscription(String name, String subscriptionId, String publisherId, String subscriberId) {
		this.name = name;
		this.publisherId = publisherId;
		this.subscriberId = subscriberId;
	}
	
	public Subscription() {
		// TODO Auto-generated constructor stub
	}
	
	public void setName(String name) {	
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setPublisherId(String publisherId) {	
		this.publisherId = publisherId;
	}
	public String getPublisherId() {
		return publisherId;
	}
	
	public void setSubscriberId(String subscriberId) {	
		this.subscriberId = subscriberId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	
}
