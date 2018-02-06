package codesquad.dto;

import codesquad.domain.Milestone;

import java.time.LocalDateTime;

public class MilestoneDto {
    private String subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public MilestoneDto() {
    }

    public MilestoneDto(String subject, LocalDateTime startTime, LocalDateTime endTime) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Milestone toMilestone() {
        return new Milestone(this.subject, this.startTime, this.endTime);
    }
}
