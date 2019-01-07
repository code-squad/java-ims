package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Lob
    @Size(min = 3)
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer, boolean deleted) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        this.deleted = deleted;
    }

    public Issue(long id, String subject, String comment, User writer, boolean deleted) {
        super(id);
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer, this.deleted);
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void update(User loginUser, IssueDto updatedIssue) {
        if (!isOwner(loginUser)) throw new UnAuthorizedException();
        this.subject = updatedIssue.getSubject();
        this.comment = updatedIssue.getComment();
    }

    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) throw new CannotDeleteException("You can't delete this issue.");
        this.deleted = true;
        List<DeleteHistory> temp = new ArrayList<>();
        temp.add(new DeleteHistory(ContentType.ISSUE, getId(), loginUser));

        return temp;
    }
}