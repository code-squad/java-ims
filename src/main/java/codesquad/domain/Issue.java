package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Size(min = 5)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {
    }

    public Issue(@Size(min = 3, max = 100) String subject, @Size(min = 5) String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(long id, @Size(min = 3, max = 100) String subject, @Size(min = 5) String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
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

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean hasSameSubjectAndComment(Issue target) {
        return subject.equals(target.subject) && comment.equals(target.comment);
    }

    @Override
    public String toString() {
        return "Issue [subject=" + subject + ", comment=" + comment + ", writer=" + writer + ']';
    }
}
