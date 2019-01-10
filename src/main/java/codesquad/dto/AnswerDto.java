package codesquad.dto;

import codesquad.domain.Answer;
import codesquad.domain.User;

public class AnswerDto {

    private String comment;

    private User writer;

    private Long id;

    public AnswerDto() {

    }

    public AnswerDto(String comment, User writer) {
        this.comment = comment;
        this.writer = writer;
    }

    public AnswerDto(Long id, String comment, User writer) {
        this(comment, writer);
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Answer _toAnswer() {
        return new Answer(this.comment, this.writer);
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "comment='" + comment + '\'' +
                ", writer=" + writer +
                ", id=" + id +
                '}';
    }
}
