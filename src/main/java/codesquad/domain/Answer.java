package codesquad.domain;

import codesquad.dto.AnswerDto;
import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Answer extends AbstractEntity {
    private static final Logger log = getLogger(Answer.class);

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Column(nullable = false)
    @Size(min = 1)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User writer, String comment, Issue issue, boolean deleted) {
        this.writer = writer;
        this.comment = comment;
        this.issue = issue;
        this.deleted = deleted;
    }

    public AnswerDto _toAnswerDto() {
        return new AnswerDto(comment, issue._toIssueDto(), writer._toUserDto(), deleted);
    }

    public AnswerDto getAnswer() {
        return _toAnswerDto();
    }

    public Issue getIssue() {
        return issue;
    }

    public Answer modify(User loginUser, String comment) {
        writer.isOwner(loginUser);
        this.comment = comment;
        return this;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", comment='" + comment + '\'' +
                ", issue=" + issue +
                ", deleted=" + deleted +
                '}';
    }

    public void isOwner(User loginUser) {
        writer.isLogin(loginUser);
        writer.equals(loginUser);
    }

    public Answer delete(User loginUser) {
        if (writer.isOwner(loginUser)) {
            deleted = true;
            return this;
        }
        throw new UnAuthorizedException();
    }
}
