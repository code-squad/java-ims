package codesquad.domain.issue.answer;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.DeleteHistory;
import codesquad.domain.issue.ContentType;
import codesquad.domain.issue.Issue;
import codesquad.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_issue"))
    @JsonIgnore
    private Issue issue;

    @Size(min = 1)
    @Lob
    private String contents;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User writer, String contents) {
        if (isLogin(writer)) {
            this.writer = writer;
            this.contents = contents;
        }
    }

    public Answer(User writer, Issue issue, String contents) {
        this(writer, contents);
        this.issue = issue;
        this.deleted = false;
    }

    public boolean isOwner(User loginUser) {
        isLogin(loginUser);
        return writer.equals(loginUser);
    }

    private boolean isLogin(User loginUser) {
        if (Objects.isNull(loginUser)) {
            // TODO security에 지정되어 있는 예외인데 왜 오류? UnAuthenticationException
            throw new UnAuthorizedException("로그인이 필요합니다.");
        }
        return true;
    }

    public DeleteHistory delete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("삭제할 수 없습니다.");
        }
        this.deleted = true;
        return new DeleteHistory(ContentType.ANSWER, getId(), loginUser, LocalDateTime.now());
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", issue=" + issue +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
