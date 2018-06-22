package codesquad.dto;

import codesquad.domain.Milestone;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MilestoneDto {
    private Long id;

    @Size(min = 3, max = 20)
    private String subject;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MilestoneDto() {
    }

    public Long getId() {
        return id;
    }

    public MilestoneDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MilestoneDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public MilestoneDto setStartDate(String startDate) {
        this.startDate = LocalDateTime.parse(startDate);
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public MilestoneDto setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
        return this;
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "title='" + subject + '\'' +
                ", startDate=" + startDate + '\'' +
                ", endDate=" + endDate + '\'' +
                '}';
    }

    public Milestone _toMilestone() {
        return new Milestone(subject, startDate, endDate);
    }
}
