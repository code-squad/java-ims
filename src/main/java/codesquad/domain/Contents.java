package codesquad.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Embeddable
public class Contents {
    @Size(min = 5, max = 200)
    @Column(nullable = false)
    private String subject;

    @Size(min = 5, max = 1000)
    @Column(nullable = false)
    private String comment;

    public Contents(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Contents update(Contents contents) {
        this.subject = contents.subject;
        this.comment = contents.comment;
        return this;
    }
}
