package org.ac.cst8277.hesse.ryan;

public class LastVisit {

	private String userId;
	private String in;
	private String out;
	
	LastVisit(String userId, String in, String out) {
		this.userId = userId;
		this.in = in;
		this.out = out;
	}
	
	public void setUserId(String userId) {	
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	
	public void setIn(String in) {	
		this.in = in;
	}
	public String getIn() {
		return in;
	}
	
	public void setOut(String out) {	
		this.out = out;
	}
	public String getOut() {
		return out;
	}
}
