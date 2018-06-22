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

    private String startDate;

    private String endDate;

    public MilestoneDto() throws ParseException {}

    public MilestoneDto(String subject, String startDate, String endDate) throws ParseException {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone _toEntity() {
        return new Milestone(subject, LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
    }

    public String getSubject() {
        return subject;
    }

    public MilestoneDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public MilestoneDto setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public MilestoneDto setEndDate(String endDate) {
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

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

