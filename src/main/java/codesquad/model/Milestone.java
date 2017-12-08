package codesquad.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Milestone {
	private final static AtomicLong autoIncrementId = new AtomicLong(3);
	@Id
	private long id;
	private String subject;
	@Column(nullable = false)
	private LocalDateTime startDate;
	@Column(nullable = false)
	private LocalDateTime endDate;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
	private User writer;

	public Milestone() {
		id = autoIncrementId.incrementAndGet();
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setStartDate(String startDate) {
		this.startDate = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
	}

	public void setEndDate(String endDate) {
		this.endDate = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getStartDate() {
		return startDate.format(DateTimeFormatter.ofPattern("MMMM dd,yyyy"));
	}
	
	public String getEndDate() {
		return endDate.format(DateTimeFormatter.ofPattern("MMMM dd,yyyy"));
	}
	
	public User getWriter() {
		return writer;
	}
}