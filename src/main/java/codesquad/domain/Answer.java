package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void writerBy(User loginUser) {
        this.writer = loginUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Answer answer1 = (Answer) o;
        return Objects.equals(writer, answer1.writer) &&
                Objects.equals(issue, answer1.issue) &&
                Objects.equals(answer, answer1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writer, issue, answer);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", issue=" + issue +
                ", answer='" + answer + '\'' +
                '}';
    }
}


