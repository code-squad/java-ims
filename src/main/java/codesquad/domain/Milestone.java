package codesquad.domain;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Milestone extends AbstractEntity {
	@Size(min = 3, max = 100)
	@Column(nullable = false, length = 100)
	private String subject;

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	@Embedded
	private Issues issues = new Issues();

	public static String convertDateTimeToString(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		return formatter.format(dateTime);
	}

	public Milestone() {
	}

	public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) throws IllegalArgumentException {
		this(0L, subject, startDate, endDate);
	}

	public Milestone(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) throws IllegalArgumentException {
		super(id);

		if (subject == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException();
		}

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

	public String getFormattedStartDate() {
		return convertDateTimeToString(startDate);
	}

	public String getFormattedEndDate() {
		return convertDateTimeToString(endDate);
	}

	public int getIssuesSize() {
		return this.issues.size();
	}

	public Milestone addIssue(Issue issue) {
		this.issues.add(issue);
		issue.registerMilestone(this);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Milestone milestone = (Milestone) o;
		return Objects.equals(subject, milestone.subject) &&
				Objects.equals(startDate, milestone.startDate) &&
				Objects.equals(endDate, milestone.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), subject, startDate, endDate);
	}

	@Override
	public String toString() {
		return "Milestone{" +
				"subject='" + subject + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}
}
