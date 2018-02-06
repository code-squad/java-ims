package codesquad.domain;

import javafx.util.converter.DateTimeStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.convert.Jsr310Converters;
import support.converter.LocalDateTimeConverter;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
public class Milestone extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Milestone.class);

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@OneToMany(mappedBy = "milestone")
	private List<Issue> issues;

	public Milestone() {
	}

	public Milestone(String subject, String startDate, String endDate) {
		this(0L, subject, startDate, endDate);
	}

	public Milestone(long id, String subject, String startDate, String endDate) {
		super(id);
		this.subject = subject;
		this.startDate = convertDate(startDate);
		this.endDate = convertDate(endDate);
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

	public void addIssue(Issue issue) {
		issue.toMilestone(this);
		issues.add(issue);
	}

	static public LocalDateTime convertDate(String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.KOREA);
		return LocalDateTime.parse(date, format);
	}

	@Override
	public String toString() {
		return "Milestone{" +
				"subject='" + subject + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
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
}
