package codesquad.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import support.domain.AbstractEntity;

@Entity
public class Milestone extends AbstractEntity {
	private String title;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
	private User writer;

	private LocalDateTime startDate;

	private LocalDateTime endDate;	
	
	public Milestone() {
	}
	
	public Milestone(String title, User writer, LocalDateTime startDate, LocalDateTime endDate) {
		this(0L, title, writer, startDate, endDate);
	}
	
	public Milestone(Long id, String title, User writer, LocalDateTime startDate, LocalDateTime endDate) {
		super(id);
		this.title = title;
		this.writer = writer;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public User getWriter() {
		return writer;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return "Milestone [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
