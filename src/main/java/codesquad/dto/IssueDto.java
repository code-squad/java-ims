package codesquad.dto;

import codesquad.domain.Issue;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class IssueDto {

    @Size(min = 3)
    private String title;
    @Size(min = 3)
    @Lob
    private String contents;

    public IssueDto(){
    }

    public IssueDto(String title, String contents){
        this.title = title;
        this.contents = contents;
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

    public Issue toIssue(){
        return new Issue(title, contents);
    }
}
