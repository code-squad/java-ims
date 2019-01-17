package codesquad.domain;

import codesquad.dto.MilestoneDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Milestone extends AbstractEntity {
    @Size(min = 3, max = 50)
    @Column(length = 50, nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
    private User writer;

    @OneToMany(mappedBy = "milestone" , fetch =FetchType.LAZY)
    @JsonIgnore
    private List<Issue> issues = new ArrayList<>();

    public Milestone() {
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate, User writer) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer = writer;
    }

    public MilestoneDto _toMilestoneDto() {
        return new MilestoneDto(this.subject, this.startDate, this.endDate, this.writer);
    }

    public MilestoneDto getMilestoneDto() {
        return _toMilestoneDto();
    }

    public List<Issue> addIssue(User loginUser, Issue issue) {
         issue.toMilestone(loginUser, this);
         issues.add(issue);
         return issues;
    }

    public long getOngoingIssues() {        //false가 진행 , true가  완료
        return issues.stream().filter(issue -> !issue.getClosed()).count();
    }

    public long getCompleteIssues() {
        return issues.stream().filter(Issue::getClosed).count();
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return subject;
    }
}
