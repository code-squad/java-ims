package codesquad.dto;

import codesquad.domain.Milestone;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class MilestoneDto {

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

    public MilestoneDto setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public MilestoneDto setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilestoneDto that = (MilestoneDto) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subject, startDate, endDate);
    }

    public Milestone toMilestone() {
        return new Milestone(subject, startDate, endDate);
    }
}
