package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

@Embeddable
public class Contents {
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String comment;

    public Contents() {
    }

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

}
