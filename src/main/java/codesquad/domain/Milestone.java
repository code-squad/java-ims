package codesquad.domain;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Milestone extends AbstractEntity {

    @Column(nullable = false, length = 200)
    @Size(min = 5, max = 200)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
    private User writer;

    @OneToMany
    private List<Issue> issues = new ArrayList<>();

    public Milestone() {

    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate, User writer) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public MilestoneDto _toMilestoneDto() {
        return new MilestoneDto(subject, startDate, endDate, writer);
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setMilestone(this);
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", writer=" + writer +
                '}';
    }
}
