package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity {
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    @Lob
    private String comment;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(0L, subject, comment);
    }

    public Issue(Long id, String subject, String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, comment);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
