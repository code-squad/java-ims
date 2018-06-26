package codesquad.domain;

import support.domain.AbstractEntity;
import support.domain.UriGeneratable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static support.domain.Entity.MILESTONE;
import static support.domain.Entity.getMultipleEntityName;
import static support.web.TimeFormat.NORMAL;
import static support.web.TimeFormat.getFormat;

@Entity
public class Milestone extends AbstractEntity implements UriGeneratable {

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Embedded
    private Issues issues = new Issues();

    public Milestone() {
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate.format(DateTimeFormatter.ofPattern(getFormat(NORMAL)));
        this.endDate = endDate.format(DateTimeFormatter.ofPattern(getFormat(NORMAL)));
    }

    public long getId() {
        return super.getId();
    }

    public String getSubject() {
        return subject;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getOpenIssueCount() {
        return issues.getOpenCount();
    }

    public int getCloseIssueCount() {
        return issues.getCloseCount();
    }

    public Milestone addIssue(Issue issue) {
        issues.add(issue);
        return this;
    }

    @Override
    public String generateUri() {
        return String.format("/%s/%d", getMultipleEntityName(MILESTONE), getId());
    }
}
