package codesquad.domain;

import codesquad.dto.AnswerDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Answer extends AbstractEntity {
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

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", comment='" + comment + '\'' +
                ", issue=" + issue +
                ", deleted=" + deleted +
                '}';
    }
}
