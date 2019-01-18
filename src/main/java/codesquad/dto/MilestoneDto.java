package codesquad.dto;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MilestoneDto {
    private String subject;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private UserDto writer;

    private String modifier;

    @JsonIgnore
    private LocalDateTime now;

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

    public String getEndDate() {
        return endDate.format(DateTimeFormatter.ofPattern("MMM dd, yyy"));
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

    public String getNow() {
        return now.format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(User loginUser) {
        this.modifier = loginUser._toUserDto().getUserId();
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
