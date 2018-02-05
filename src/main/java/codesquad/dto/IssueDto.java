package codesquad.dto;

import codesquad.domain.Issue;

public class IssueDto {
    private String title;
    private String comment;

    public IssueDto() {

    }

    public String getTitle() {
        return title;
    }

    public IssueDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public IssueDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Object toIssue() {
        return new Issue()
                .setTitle(title)
                .setComment(comment);
    }
}
