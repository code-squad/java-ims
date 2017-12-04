package codesquad.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Issue {
	private String subject;
	private String comment;
	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private Date regDate;

	public Issue(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
		id = count.incrementAndGet();
		regDate = new Date();
		
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getComment() {
		return comment;
	}
	
	public String getRegDate() {
		return regDate.toString();
	}
	
	public int getId() {
		return id;
	}

	public String toString() {
		return "subject : " + subject + " comment : " + comment +" date : " + regDate;
	}
}
