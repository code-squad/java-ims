package codesquad.dto;

import codesquad.domain.Milestone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MilestoneDto {
	private static final Logger log = LoggerFactory.getLogger(MilestoneDto.class);

	@Size(min = 3, max = 100)
	private String subject;

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public static LocalDateTime convertStringToDateTime(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		return LocalDateTime.parse(str, formatter);
	}

	public MilestoneDto() {
	}

	public MilestoneDto(String subject, String startDate, String endDate) {
		this.subject = subject;
		this.startDate = convertStringToDateTime(startDate);
		this.endDate = convertStringToDateTime(endDate);
	}

	public String getSubject() {
		return subject;
	}

	public MilestoneDto setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public MilestoneDto setStartDate(String startDate) {
		this.startDate = convertStringToDateTime(startDate);
		return this;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public MilestoneDto setEndDate(String endDate) {
		this.endDate = convertStringToDateTime(endDate);
		return this;
	}

	public Milestone _toMilestone() throws IllegalArgumentException {
		return new Milestone(this.subject, this.startDate, this.endDate);
	}

	@Override
	public String toString() {
		return "MilestoneDto{" +
				"subject='" + subject + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}
}
