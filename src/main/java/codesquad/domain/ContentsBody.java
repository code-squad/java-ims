package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.util.Objects;

@Embeddable
public class ContentsBody {
    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String subject;

    @Size(min = 5, max = 100)
    @Column(nullable = false, length = 100)
    private String comment;

    public ContentsBody() {
    }

    public ContentsBody(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public void update(ContentsBody contentsBody) {
        this.comment = contentsBody.comment;
        this.subject = contentsBody.subject;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentsBody contentsBody = (ContentsBody) o;
        return Objects.equals(subject, contentsBody.subject) &&
                Objects.equals(comment, contentsBody.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, comment);
    }
}
