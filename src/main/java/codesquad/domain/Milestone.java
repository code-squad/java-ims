package codesquad.domain;

import javafx.util.converter.DateTimeStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.convert.Jsr310Converters;
import support.converter.LocalDateTimeConverter;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
public class Milestone extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Milestone.class);

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

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

	static public LocalDateTime convertDate(String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm", Locale.KOREA);
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
}
