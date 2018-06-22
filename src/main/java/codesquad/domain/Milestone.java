package codesquad.domain;

import codesquad.exception.AlreadyAssignException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Milestone extends AbstractEntity implements UriGeneratable{

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    @Size(min = 5)
    private String subject;

    @JsonIgnore
    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    private List<Issue> issues;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
    private User writer;

    public Milestone() {

    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone(long id, LocalDateTime startDate, LocalDateTime endDate, List<Issue> issues) {
        super(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.issues = issues;
    }

    public Milestone writedBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public String getFormattedStartDate() {
        return getFormattedDate(startDate, "MMM d, uuuu");
    }

    public String getFormattedEndDate() {
        return getFormattedDate(endDate, "MMM d, uuuu");
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void assignIssue(Issue issue) throws AlreadyAssignException {
        if (!issues.stream().allMatch(i -> i.getId() == issue.getId())) {
            throw new AlreadyAssignException();
        }
        issues.add(issue);
    }

    public long getNumberOfOpenIssues() {
        return issues.stream().filter(i -> i.isOpen()).count();
    }

    public Long getNumberOfClosedIssues() {
        return issues.stream().filter(i -> i.isClosed()).count();
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String generateUrl() {
        return String.format("/milstones/", getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Milestone milestone = (Milestone) o;
        return Objects.equals(startDate, milestone.startDate) &&
                Objects.equals(endDate, milestone.endDate) &&
                Objects.equals(issues, milestone.issues);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), startDate, endDate, issues);
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", issues=" + issues +
                '}';
    }

}
