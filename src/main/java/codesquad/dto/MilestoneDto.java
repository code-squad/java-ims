package codesquad.dto;

import codesquad.domain.Milestone;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MilestoneDto {
    private String subject;
    private LocalDate startTime;
    private LocalDate endTime;

    public MilestoneDto() {
    }

    public MilestoneDto(String subject, LocalDate startTime, LocalDate endTime) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Milestone toMilestone() {
        return new Milestone(this.subject, this.startTime, this.endTime);
    }
}
