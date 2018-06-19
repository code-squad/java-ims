package codesquad.dto;

import codesquad.domain.Milestone;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MilestoneDto {

    private String subject;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public MilestoneDto() throws ParseException {}

    public MilestoneDto(String subject, String startDate, String endDate) throws ParseException {
        this.subject = subject;
        this.startDate = LocalDateTime.parse(startDate);
        this.endDate = LocalDateTime.parse(endDate);
    }

    public Milestone _toEntity() {
        return new Milestone(subject, startDate, endDate);
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
}

