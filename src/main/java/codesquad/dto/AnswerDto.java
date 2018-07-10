package codesquad.dto;

import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.Lob;

public class AnswerDto extends AbstractEntity {
    private static final Logger log =  LoggerFactory.getLogger(AnswerDto.class);

    private User writer;
    private Issue issue;

    @Lob
    private String contents;

    public AnswerDto() {}

    public AnswerDto(User writer, Issue issue, String contents) {
        this.writer = writer;
        this.issue = issue;
        this.contents = contents;
    }

    public AnswerDto(long id, User writer, Issue issue, String contents) {
        super(id);
        this.writer = writer;
        this.issue = issue;
        this.contents = contents;
    }

    public Answer toAnswer() {
        return new Answer(this.writer, this.issue, this.contents);
    }

    public User getWriter() {
        return writer;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getContents() {
        return contents;
    }

    public AnswerDto setWriter(User writer) {
        this.writer = writer;
        return this;
    }

    public AnswerDto setIssue(Issue issue) {
        this.issue = issue;
        return this;
    }

    public AnswerDto setContents(String contents) {
        this.contents = contents;
        return this;
    }
}
