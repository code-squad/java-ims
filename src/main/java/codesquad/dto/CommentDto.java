package codesquad.dto;

import codesquad.domain.Comment;
import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class CommentDto {

    private long id;

    @Size(min = 3)
    private String comment;

    private User writer;
    private Issue issue;

    public CommentDto() {
    }

    public CommentDto(String comment) {
        this.comment = comment;
    }

    public CommentDto(Long id, String comment, User writer, Issue issue) {
        this.id = id;
        this.comment = comment;
        this.writer = writer;
        this.issue = issue;
    }

    public long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    public Issue getIssue() {
        return issue;
    }

    public CommentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public CommentDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public CommentDto setWriter(User writer) {
        this.writer = writer;
        return this;
    }

    public CommentDto setIssue(Issue issue) {
        this.issue = issue;
        return this;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", writer=" + writer.getName() +
                ", issue=" + issue.getSubject() +
                '}';
    }

    public Comment _toComment() {
        if (writer == null && issue == null) {
            return new Comment(comment);
        }
        return new Comment(comment, writer, issue);
    }
}
