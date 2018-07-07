package codesquad.domain;

import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonProperty;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_issue"))
    @JsonProperty
    private Issue issue;

    @Size(min = 5)
    @Lob
    @JsonProperty
    private String contents;

    public Answer() {
    }

    public Answer(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
    }

    public Answer(User writer, Issue issue, String contents) {
        this.writer = writer;
        this.issue = issue;
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public Issue getQuestion() {
        return issue;
    }

    public String getContents() {
        return contents;
    }

    void applyToIssue(Issue issue) {
        this.issue = issue;
    }

    boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void checkAnswerDelete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("답변 작성자와 로그인 유저가 다름");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(writer, answer.writer) &&
                Objects.equals(issue, answer.issue) &&
                Objects.equals(contents, answer.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writer, issue, contents);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", issue=" + issue +
                ", contents='" + contents + '\'' +
                '}';
    }
}