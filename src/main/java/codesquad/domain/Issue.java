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

    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String subject;

    @Size(min = 3, max = 500)
    @Column(nullable = false, length = 500)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_Issue_writter"))
    private User writer;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String content) {
        this.subject = subject;
        this.comment = content;
    }

    public Issue(long id, String subject, String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String subject, String comment, User writer, boolean deleted) {
        this(0L, subject, comment, writer, deleted);
    }

    public Issue(long id, String subject, String content, User writer, boolean deleted) {
        super(id);
        this.subject = subject;
        this.comment = content;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void update(User loginUser, Issue toIssue) {
        if (!matchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassord(loginUser.getPassword())) {
            return;
        }
        this.subject = toIssue.subject;
        this.comment = toIssue.comment;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    private boolean matchPassord(String password) {
        return this.writer.getPassword().equals(password);
    }

    private boolean matchWriter(User loginUser) {
        return this.writer.getUserId().equals(loginUser.getUserId());
    }

//    ApiIssueController에서 사용할 것이지만 일단은 만들어 보았다.
    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer);
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!matchWriter(loginUser)) {
            throw new CannotDeleteException("작성자가 아니면 지울수 없습니다.");
        }
        this.deleted = true;
        List<DeleteHistory> deletes = new ArrayList<>();
        deletes.add(new DeleteHistory(ContentType.ISSUE, getId(), writer));
        return deletes;
    }
}
