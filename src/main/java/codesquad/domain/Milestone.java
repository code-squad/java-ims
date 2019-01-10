package codesquad.domain;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Milestone extends AbstractEntity {
    @Size(min = 3, max = 50)
    @Column(length = 50, nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
    private User writer;

    public Milestone() {
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate, User writer) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer = writer;
    }

    public MilestoneDto _toMilestoneDto() {
        return new MilestoneDto(this.subject, this.startDate, this.endDate, this.writer);
    }

    public MilestoneDto getMilestoneDto() {
        return _toMilestoneDto();
    }

    public String getDate() {
        return getFormattedDate(endDate, "MMM dd, yyyy");
    }



}
