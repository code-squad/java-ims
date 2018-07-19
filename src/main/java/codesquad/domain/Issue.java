package codesquad.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @Size(min = 1, max = 50)
    @Column(length = 50)
    private String subject;

    @Size(min = 1, max = 200)
    @Column(length = 200)
    private String contents;

    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @Embedded
    private Assignees assignees = new Assignees();

    @Embedded
    private Labels labels = new Labels();

    @OneToMany
    @JoinColumn(name = "issueId")
    private List<Comment> comments = new ArrayList<>();

    Boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String contents) {
        this(subject, contents, null);
    }

    public Issue(Long id, String subject, String comment) {
        this(id, subject, comment, null);
    }

    public Issue(Long id, String subject, String comment, User writer) {
        this.id = id;
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public String getSubject() {
        return subject;
    }

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void writeBy(User loginedUser) {
        writer = loginedUser;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", writer=" + writer +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Issue update(Issue updateIssue) {
        if (!this.writer.equals(updateIssue.writer)) {
            throw new IllegalArgumentException("Cannot match user");
        }
        this.subject = updateIssue.subject;
        this.comment = updateIssue.comment;
        return this;
    }

    public void deleted() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
