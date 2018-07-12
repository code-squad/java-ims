package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {

    private long id;

    @Size (min = 3, max = 100)
    private String title;

    @Size (min = 3)
    private String contents;

    public IssueDto() {
    }

    public IssueDto(String title, String contents) {
        this(0, title, contents);
    }

    public IssueDto(long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Issue toIssue() {
        return new Issue(this.title, this.contents);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueDto issueDto = (IssueDto) o;
        return Objects.equals(title, issueDto.title) &&
                Objects.equals(contents, issueDto.contents);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, contents);
    }
}
