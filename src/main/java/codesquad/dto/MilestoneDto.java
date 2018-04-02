package codesquad.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import codesquad.domain.Milestone;

public class MilestoneDto {
	private String subject;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Milestone _toMilestone() {
		return new Milestone(subject, startDate, endDate);
	}

	@Override
	public String toString() {
		return "MilestoneDto [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
