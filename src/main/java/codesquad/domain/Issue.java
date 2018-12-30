package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import support.domain.AbstractEntity;
import support.domain.UrlGeneratable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity implements UrlGeneratable {
    private static final Logger log = getLogger(Issue.class);

    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Size(min = 5)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(long id, String subject, String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
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

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void writtenBy(User loginUser) {
        this.writer = loginUser;
    }

    public boolean hasSameSubjectAndComment(Issue target) {
        return subject.equals(target.subject) && comment.equals(target.comment);
    }

    public boolean isOwner(User target) {
        return writer.equals(target);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String generateUrl() {
        return String.format("/issues/%d", getId());
    }

    @Override
    public String toString() {
        return "Issue [subject=" + subject + ", comment=" + comment + ", writer=" + writer + ']';
    }

    public Issue update(User loginUser, Issue updateIssue) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        this.subject = updateIssue.subject;
        this.comment = updateIssue.comment;
        return this;
    }

    public DeleteHistory delete(User loginUser) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        deleted = true;
        return new DeleteHistory(ContentType.ISSUE, getId(), loginUser);
    }
}
