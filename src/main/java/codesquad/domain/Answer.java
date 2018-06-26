package codesquad.domain;

import codesquad.dto.AnswerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
@Table
public class Answer extends AbstractEntity {
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
    private String contents;

    public Answer() {}

    public Answer(User writer, Issue issue, String contents) {
        this.writer = writer;
        this.issue = issue;
        this.contents = contents;
    }

    public Answer(long id,User writer, Issue issue, String contents) {
        super(id);
        this.writer = writer;
        this.issue = issue;
        this.contents = contents;
    }

    public void toIssue(Issue issue) {
        this.issue = issue;
    }

    public AnswerDto toAnswerDto() {
        return new AnswerDto(this.writer, this.issue, this.contents);
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

    public String getContents() {
        return contents;
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
