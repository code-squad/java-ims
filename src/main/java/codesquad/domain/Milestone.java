package codesquad.domain;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3)
    @Column(nullable = false, length = 40)
    private String subject;

    @Column(nullable = false, length = 20)
    private LocalDateTime startDate;

    @Column(nullable = false, length = 20)
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

    public MilestoneDto toMilestoneDto() {
        return new MilestoneDto(subject, startDate, endDate);
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public int getOpen() {
        return issues.numberOfOpen();
    }

    public int getClosed() {
        return issues.sizeOfIssues() - getOpen();
    }
}
