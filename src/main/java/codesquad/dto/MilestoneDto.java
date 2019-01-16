package codesquad.dto;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import support.converter.LocalDateTimeConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MilestoneDto {

    @Size(min = 5, max = 200)
    @NotBlank
    private String subject;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private User writer;

    public MilestoneDto() {

    }

    public MilestoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MilestoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate, User writer) {
        this(subject, startDate, endDate);
        this.writer = writer;
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Milestone _toMilestone(User writer) {
        return new Milestone(subject, startDate, endDate, writer);
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
