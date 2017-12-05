package codesquad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Issue {
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable=false)
	private String subject;
	@Lob
	private String comment;
	@Column(nullable=false)
	private String writer;
	@CreatedDate
	@Column(nullable=false)
	private Date regDate;
	@LastModifiedDate
	private Date modifiedDate;

	public Issue() {
		regDate = new Date();
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
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

	public long getId() {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issue other = (Issue) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "subject : " + subject + " comment : " + comment + " date : " + regDate;
	}
}
