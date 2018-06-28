package codesquad.dto;

import codesquad.domain.Comment;
import codesquad.domain.User;

public class CommentDto {

    private String comment;

    public CommentDto(){
    }

    public CommentDto(String comment){
        this.comment = comment;
    }

    public Comment toComment(){
        return new Comment(comment);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
