package codesquad.dto;

import codesquad.domain.Label;
import codesquad.domain.User;
import javax.validation.constraints.Size;

public class LabelDto {
    @Size(min = 5, max = 20)
    private String subject;

    private User writer;

    public LabelDto() {

    }

    public LabelDto(String subject, User writer) {
        this.subject = subject;
        this.writer = writer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Label _toLabel(User writer) {
        return new Label(this.subject, writer);
    }
}
