package codesquad.dto;

import codesquad.domain.Milestone;

import java.time.LocalDateTime;
import java.util.Objects;

public class MilestoneDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public MilestoneDto() {}

    public MilestoneDto(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Milestone _toEntity() {
        return new Milestone(startDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilestoneDto that = (MilestoneDto) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
