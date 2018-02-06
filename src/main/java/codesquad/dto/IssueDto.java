package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

public class IssueDto {
    private String title;
    private String comment;
    private User author;

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

    public User getAuthor() {
        return author;
    }

    public IssueDto setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Issue toIssue() {
        return new Issue(title, comment, author);
    }
}
