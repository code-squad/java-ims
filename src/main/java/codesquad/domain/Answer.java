package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;
import support.domain.UriGeneratable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity implements UriGeneratable {

    @Size(min = 5)
    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    public Answer() {}

    public Answer(String content) {
        this.content = content;
    }

    public Answer writtenBy(User writer) {
        this.writer = writer;
        return this;
    }

    public Answer toIssue(Issue issue) {
        if(this.issue != null) {
            this.issue.removeAnswer(this);
        }
        this.issue = issue;
        return this;
    }

    public User getWriter() {
        return writer;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String generateUrl() {
        return "/answers/" + getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(content, answer.content) &&
                Objects.equals(writer, answer.writer) &&
                Objects.equals(issue, answer.issue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), content, writer, issue);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "content='" + content + '\'' +
                ", writer=" + writer.getName() +
                ", id=" + getId() +
                '}';
    }
}
