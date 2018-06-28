package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.dto.AnswerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Answer extends AbstractEntity {
    private static final Logger log =  LoggerFactory.getLogger(Answer.class);

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    @JsonProperty
    private Issue issue;

    @Lob
    @JsonProperty
    private String comment;

    private boolean deleted = false;

    public Answer() {}

    public Answer(User writer, Issue issue, String comment) {
        this.writer = writer;
        this.issue = issue;
        this.comment = comment;
        getFormattedCreateDate();
    }

    public Answer(long id, User writer, Issue issue, String comment) {
        super(id);
        this.writer = writer;
        this.issue = issue;
        this.comment = comment;
    }

    public void update(User loginUser, AnswerDto answerDto) throws CannotDeleteException {
        if (!isSameWriter(loginUser))
            throw new CannotDeleteException("자신이 쓴 댓글만 수정할 수 있습니다.");
        this.comment = answerDto.getContents();
        log.info("updated is {}", comment);
    }

    public DeleteHistory delete(User loginUser) throws CannotDeleteException {
        if (!isSameWriter(loginUser))
            throw new CannotDeleteException("자신이 쓴 댓글만 삭제할 수 있습니다.");
        deleted = true;
        log.info("삭제 성공 : {}", toString());
        return new DeleteHistory(ContentType.ANSWER, getId(), loginUser, LocalDateTime.now());
    }

    public void toIssue(Issue issue) {
        this.issue = issue;
    }

    public AnswerDto toAnswerDto() {
        return new AnswerDto(this.writer, this.issue, this.comment);
    }

    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }

    public User getWriter() {
        return writer;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getComment() {
        return comment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", issue=" + issue +
                ", comment='" + comment + '\'' +
                '}';
    }


}
