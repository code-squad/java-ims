package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {

    private long id;

    @NotNull
    private User writer;

    @Size(min = 3)
    private String title;

    @Size(min = 3)
    private String content;

    public IssueDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Issue _toIssue() {
        return new Issue(title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueDto)) return false;
        IssueDto issueDto = (IssueDto) o;
        return Objects.equals(writer, issueDto.writer) &&
                Objects.equals(title, issueDto.title) &&
                Objects.equals(content, issueDto.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(writer, title, content);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
