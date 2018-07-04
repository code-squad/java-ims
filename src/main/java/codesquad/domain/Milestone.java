package codesquad.domain;

import codesquad.dto.MilestoneDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Milestone extends AbstractEntity {
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @JsonIgnore
    @Embedded
    private Issues issues;

    public Milestone() {
    }

    public Milestone(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
        super(id);
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone(MilestoneDto milestoneDto) {
        subject = milestoneDto.getSubject();


        startDate = LocalDateTime.parse(milestoneDto.getStartDate());
        endDate = LocalDateTime.parse(milestoneDto.getEndDate());
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

    public Issues getIssues() {
        return issues;
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
