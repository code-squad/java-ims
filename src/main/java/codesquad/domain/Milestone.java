package codesquad.domain;

import codesquad.dto.MilestoneDto;
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

    @Embedded
    private Issues issues;

    public Milestone() {

    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStart() {
        return startDate;
    }

    public LocalDateTime getEnd() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "id=" + getId() +
                ", start=" + startDate +
                ", end=" + endDate +
                '}';
    }

    public String generateUrl() {
        return "/api/milestones/" + getId();
    }

    public void registerIssue(Issue issue) {
        issues.add(issue);
    }

    public MilestoneDto toDto() {
        return new MilestoneDto().setSubject(subject)
                .setStartDate(startDate)
                .setEndDate(endDate);
    }
}
