package codesquad.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Issue {
	@Id
	@GeneratedValue
	private int id;
	private String subject;
	@Lob
	private String comment;
	private String writer;
	private Date regDate;

	public Issue() {
		regDate = new Date();
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getWriter() {
		return writer;
	}

	public boolean isWriter(String id) {
		return writer.equals(id);
	}

	public void update(String subject, String comment) {
		this.comment = comment;
		this.subject = subject;
		regDate = new Date();
	}

	@Override
	public String toString() {
		return "subject : " + subject + " comment : " + comment + " date : " + regDate;
	}
}
