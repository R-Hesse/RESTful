package org.ac.cst8277.hesse.ryan;



public class Message {

	private String messageId;
	private String dog;
	private String content;
	private String createdOn;
	private String pubId;
	private String token;
	private String name;

	
	Message(String messageId, String dog, String content, String createdOn, String pubId) {
		this.messageId = messageId;
		this.dog= dog;
		this.content = content;
		this.createdOn = createdOn;
		this.pubId = pubId;
	}
	public void Message1(String name, String token, String pubId, String dog, String content) {
		this.name = name;
		this.token= token;
		this.pubId = pubId;
		this.dog = dog;
		this.content = content;
	}

	public Message() {
	}

	public void createMessage() {
		
	}
	
	public void updateMessage() {
		
	}
	
	public void deleteMessage() {
		
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessageId() {
		return messageId;
	}
	
	public void setDog(String dog) {
		this.dog = dog;
	}
	public String getDog() {
		return dog;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public String getPubId() {
		return pubId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	
}
