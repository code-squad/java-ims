package codesquad.dto;

import codesquad.domain.Milestone;
import codesquad.domain.User;

import java.time.LocalDateTime;

public class MilestoneDto {
    private String subject;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private UserDto writer;

    public MilestoneDto() {
    }

    public MilestoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate, User writer) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer = writer._toUserDto();
    }

    public Milestone _toMilestone(User loginUser) {
        return new Milestone(subject, startDate, endDate, loginUser);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public UserDto getWriter() {
        return writer;
    }

    public void setWriter(UserDto writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", writer=" + writer +
                '}';
    }
}
