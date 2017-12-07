package codesquad.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Issue {
	private final static AtomicLong autoIncrementId = new AtomicLong(4);
	@Id
	private long id;
	@Column(nullable = false)
	private String subject;
	@Lob
	private String comment;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime regDate;
	@LastModifiedDate
	@Column
	private LocalDateTime modifiedDate;

	public Issue() {
		id = autoIncrementId.incrementAndGet();
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSubject() {
		return subject;
	}

	public String getComment() {
		return comment;
	}

	public String getDate() {
		if (modifiedDate == null) {
			return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public long getId() {
		return id;
	}

	public User getWriter() {
		return writer;
	}

	public boolean isWriter(User user) {
		return writer.equals(user);
	}

	public void updateSubject(String subject) {
		this.subject = subject;
	}

	public void updateComment(String comment) {
		this.comment = comment;
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
