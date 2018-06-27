package codesquad.dto;

import codesquad.domain.Comment;

import javax.validation.constraints.Size;

public class CommentDto {

    @Size(min = 3)
    private String comment;

    public CommentDto() {
    }

    public CommentDto(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public CommentDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Comment _toComment() {
        return new Comment(comment);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
