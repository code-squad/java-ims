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

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_issue"))
    private Issue issue;

    @Size(min = 5)
    @Lob
    private String contents;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(String contents) {
        this.contents = contents;
        this.deleted = false;
    }

    public Answer(long id, String contents) {
        super(id);
        this.contents = contents;
        this.deleted = false;
    }

    public Answer(Long id, User writer, Issue issue, String contents) {
        super(id);
        this.writer = writer;
        this.issue = issue;
        this.contents = contents;
        this.deleted = false;
    }


    public User getWriter() {
        return writer;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getContents() {
        return contents;
    }

    public void toIssue(Issue issue) {
        this.issue = issue;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public AnswerDto toAnswerDto() {
        return new AnswerDto(getId(), this.contents);
    }

    public String generateUrl() {
        return String.format("%s/answers/%d", issue.generateUrl(),getId());
    }


    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + ", deleted=" + deleted + "]";
    }
}
