package codesquad.domain;

import codesquad.dto.AnswerDto;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {

    @Column(nullable = false)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    private boolean deleted = false;

    public Answer() {

    }

    public Answer(String comment, User writer) {
        this.comment = comment;
        this.writer = writer;
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

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Answer applyWriter(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public boolean isOneSelf(User loginUser) {
        if(!this.writer.equals(loginUser)) {
            return false;
        }
        return true;
    }

    public Answer update(Answer updatedAnswer) {
        this.comment = updatedAnswer.comment;
        return this;
    }

    public AnswerDto _toAnswerDto() {
        return new AnswerDto(this.getId(), this.comment, this.writer);
    }
}
