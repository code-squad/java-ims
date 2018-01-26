package codesquad.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

@Entity
public class Milestone extends AbstractEntity {
	@Column(nullable = false, length = 20)
	private String title;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
	private User writer;

	private LocalDateTime startDate;
	
	@Column(nullable = false)
	private LocalDateTime endDate;	
	
	public Milestone() {
	}

	public Milestone(String title, User writer, LocalDateTime startDate, LocalDateTime endDate) {
		super(0L);
		this.title = title;
		this.writer = writer;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public MilestoneDto _toMilestoneDto() {
		return new MilestoneDto(super.getId(), this.title, this.writer, this.startDate, this.endDate);
	}

	@Override
	public String toString() {
		return "Milestone [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
