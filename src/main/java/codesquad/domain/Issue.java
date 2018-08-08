package codesquad.domain;

import codesquad.dto.IssueDto;

import javax.persistence.*;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    User writer;

    String subject;

    String comment;

    Boolean deleted = false;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    Milestone milestone;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(null, subject, comment, null);
    }

    public Issue(String subject, String comment, User writer) {
        this(null, subject, comment, writer);
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

    public String getComment() {
        return comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", writer=" + writer +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", milestone='" + milestone + '\'' +
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
}
