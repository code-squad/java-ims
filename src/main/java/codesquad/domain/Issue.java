package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private boolean deleted = false;

    public Issue() {

    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public boolean isMatchWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void update(User loginUser, Issue target) {
        if (!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.subject = target.subject;
        this.comment = target.comment;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!isMatchWriter(loginUser)) {
            throw new CannotDeleteException("작성자만 삭제 가능합니다.");
        }
        this.deleted = true;

        List<DeleteHistory> temp = new ArrayList<>();
        temp.add(new DeleteHistory(ContentType.ISSUE, getId(), writer));
        return temp;
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
                "id='" + getId() + '\'' +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
