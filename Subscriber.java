package org.ac.cst8277.hesse.ryan;

public class Subscriber {
	
	private String subscriberId;
	private String comment;

	Subscriber(String subscriberId, String comment) {
		this.subscriberId = subscriberId;
		this.comment = comment;
	}
	
	public Subscriber() {
		// TODO Auto-generated constructor stub
	}

	public void setSubscriberId(String subscriberId) {	
		this.subscriberId = subscriberId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
	
}
