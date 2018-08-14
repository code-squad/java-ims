package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
    private String comment;

    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @Embedded
    private Assignees assignees = new Assignees();

    @Embedded
    private Labels labels = new Labels();

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(0L, subject, comment, null);
    }

    public Issue(String subject, String comment, User writer) {
        this(0L, subject, comment, writer);
    }

    public Issue(Long id, String subject, String comment, User writer) {
        super(id);
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + getId() +
                ", writer=" + writer +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", milestone='" + milestone + '\'' +
                ", assignee='" + assignees + '\'' +
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

    public Issue update(IssueDto updateIssueDto, User updateWriter) {
        if (!this.writer.equals(updateWriter)) {
            throw new IllegalArgumentException("Cannot match user");
        }
        this.subject = updateIssueDto.getSubject();
        this.comment = updateIssueDto.getComment();
        return this;
    }

    public void deleted() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean matchWriter(User writer) {
        return this.writer.equals(writer);
    }

    public void registerMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void registerAssignee(User user) {
        assignees.addAssignee(user);
    }

    public void registerLabel(Label label) {
        labels.addLabel(label);
    }
}
