package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {

    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public Issue(long id, String subject, String comment, User writer) {
        super(id);
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

    public User getWriter() {
        return writer;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + getId() + '\'' +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
