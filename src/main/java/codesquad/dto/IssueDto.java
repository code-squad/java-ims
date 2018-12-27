package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3)
    private String contents;

    private User writer;

    public IssueDto() {

    }

    public IssueDto(String title, String contents, User writer) {
        super();
        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Issue _toIssue() {
        return new Issue(this.title, this.contents, this.writer);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                '}';
    }
}
