package codesquad.domain;

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

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(null, subject, comment);
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

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
