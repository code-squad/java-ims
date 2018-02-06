package codesquad.dto;

import codesquad.domain.Milestone;

import java.time.LocalDateTime;

public class MilestoneDto {
	private String subject;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public MilestoneDto() {
	}

	public MilestoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate) {
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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public MilestoneDto setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
		return this;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public MilestoneDto setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
		return this;
	}

	public Milestone toMilestone() {
		return new Milestone(this.subject, this.startDate, this.endDate);
	}
}
