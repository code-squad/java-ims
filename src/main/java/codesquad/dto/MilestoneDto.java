package codesquad.dto;

import codesquad.domain.Milestone;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MilestoneDto {
    private Long id;

    @Size(min = 3, max = 20)
    private String title;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MilestoneDto() {
    }

    public Long getId() {
        return id;
    }

    public MilestoneDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MilestoneDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public MilestoneDto setStartDate(String startDate) {
        this.startDate = LocalDateTime.parse(startDate);
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public MilestoneDto setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
        return this;
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate + '\'' +
                ", endDate=" + endDate + '\'' +
                '}';
    }

    public Milestone _toMilestone() {
        return new Milestone(title, startDate, endDate);
    }
}
