package codesquad.domain;

import codesquad.dto.LabelDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Label extends AbstractEntity {
    @Column(nullable = false)
    private String subject;

    @JsonIgnore
    @Embedded
    private Issues issues;

    public Label() {
    }

    public Label(long id, String subject) {
        super(id);
        this.subject = subject;
    }

    public Label(String subject) {
        this.subject = subject;
    }

    public Label(LabelDto labelDto) {
        subject = labelDto.getSubject();
    }

    public String getSubject() {
        return subject;
    }

    public Issues getIssues() {
        return issues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Label label = (Label) o;
        return Objects.equals(subject, label.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subject);
    }

    @Override
    public String toString() {
        return "Label{" +
                "subject='" + subject + '\'' +
                ", issues=" + issues +
                '}';
    }
}
