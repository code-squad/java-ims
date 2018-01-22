package codesquad.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import codesquad.domain.Milestone;
import codesquad.domain.User;

public class MilestoneDto {
	@NotNull
	private String title;
	private User writer;
	@NotNull
	private LocalDateTime startDate;
	@NotNull
	private LocalDateTime endDate;

	public MilestoneDto() {
	}

	public User getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public void addWriter(User loginUser) {
		this.writer = loginUser;
	}
	
	public Milestone _toMilestone() {
		return new Milestone(this.title, this.writer, this.startDate, this.endDate);
	}

	@Override
	public String toString() {
		return "MilestoneDto [writer=" + writer + ", title=" + title + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
}
