package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Milestone extends AbstractEntity {
	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	public Milestone() {
	}

	public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
		this(0L, subject, startDate, endDate);
	}

	public Milestone(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
		super(id);
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getSubject() {
		return subject;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}
}
