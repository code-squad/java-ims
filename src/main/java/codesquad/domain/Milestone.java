package codesquad.domain;

import codesquad.dto.MilestoneDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3)
    @Column(nullable = false, length = 40)
    private String subject;

    @JsonIgnore
    @Column(nullable = false, length = 20)
    private LocalDateTime startDate;

    @JsonIgnore
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

    public long getOpen() {
        return issues.numberOfOpen();
    }

    public long getClosed() {
        return issues.sizeOfIssues() - getOpen();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Milestone milestone = (Milestone) o;
        return Objects.equals(subject, milestone.subject) &&
                Objects.equals(startDate, milestone.startDate) &&
                Objects.equals(endDate, milestone.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subject, startDate, endDate);
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
