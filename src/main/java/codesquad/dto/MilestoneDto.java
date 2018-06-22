package codesquad.dto;

import codesquad.domain.Milestone;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MilestoneDto {

    @Size(min = 6)
    private String subject;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


    public MilestoneDto() {
    }

    public MilestoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone toMilestone() {
        return new Milestone(subject, startDate, endDate);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartDate() {
        return startDate.toString();
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDateTime.parse(startDate);
    }

    public String getEndDate() {
        return endDate.toString();
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}


