package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity implements UriGeneratable{

    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false, length = 50)
    private String subject;

    @Size(min = 5)
    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue () {}

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String subject, String comment, User writer) {
        this(0L, subject, comment, writer);
    }

    public Issue(long id, String subject, String comment, User writer) {
        super(id);
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public Issue writeBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(subject, comment, writer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment) &&
                Objects.equals(writer, issue.writer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), subject, comment, writer);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }

    public boolean isWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public Issue update(IssueDto target) {
        this.subject = target.getSubject();
        this.comment = target.getComment();
        return this;
    }

    @Override
    public String generateUrl() {
        return String.format("/issues/%d", getId());
    }
}

