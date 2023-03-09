package org.ac.cst8277.hesse.ryan;

public class Publisher {
	
	private String publisherId;
	private String dogName;
	private String comment;
	
	Publisher(String publisherId, String dogName, String comment) {
		this.publisherId = publisherId;
		this.dogName = dogName;
		this.comment = comment;
	}
	
	public Publisher() {

	}

	public void setPublisherId(String publisherId) {	
		this.publisherId = publisherId;
	}
	public String getPublisherId() {
		return publisherId;
	}
	
	public void setDogName(String dogName) {	
		this.dogName = dogName;
	}
	public String getDogName() {
		return dogName;
	}

	public void setComment(String comment) {	
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
}
