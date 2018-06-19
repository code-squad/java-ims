package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Milestone extends AbstractEntity implements UriGeneratable{

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    private List<Issue> issues;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
    private User writer;

    public Milestone() {

    }

    public Milestone(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone(LocalDateTime startDate, LocalDateTime endDate, List<Issue> issues) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.issues = issues;
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
        return getFormattedDate(startDate, "d MMM, uuuu");
    }

    public String getFormattedEndDate() {
        return getFormattedDate(endDate, "d MMM, uuuu");
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public long getNumberOfOpenIssues() {
        return issues.stream().filter(i -> i.isOpen()).count();
    }

    public Long getNumberOfClosedIssues() {
        return issues.stream().filter(i -> i.isClosed()).count();
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
