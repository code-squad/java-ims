package codesquad.dto;

import codesquad.domain.Milestone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class MilestoneDto {
	private static final Logger log = LoggerFactory.getLogger(MilestoneDto.class);

	private String subject;
	private String startDate;
	private String endDate;

	public MilestoneDto() {
	}

	public MilestoneDto(String subject, String startDate, String endDate) {
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getSubject() {
		return subject;
	}

	public MilestoneDto setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getStartDate() {
		return startDate;
	}

	public MilestoneDto setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public String getEndDate() {
		return endDate;
	}

	public MilestoneDto setEndDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public Milestone toMilestone() {
		return new Milestone(this.subject, this.startDate, this.endDate);
	}

	@Override
	public String toString() {
		return "MilestoneDto{" +
				"subject='" + subject + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				'}';
	}
}
