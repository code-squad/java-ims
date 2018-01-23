package codesquad.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import codesquad.domain.Milestone;
import codesquad.domain.User;

public class MilestoneDto {
	private long id;
	@NotNull
	@Size(min = 3, max = 20)
	private String title;
	private User writer;
	@NotNull
	private LocalDateTime startDate;
	@NotNull
	private LocalDateTime endDate;

	public MilestoneDto() {
	}

	public MilestoneDto(long id, String title, User writer, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setWriter(User writer) {
		this.writer = writer;
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
