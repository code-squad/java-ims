package codesquad.dto;

import javax.persistence.Column;
import java.util.Objects;

public class LabelDto {
    @Column(nullable = false)
    private String subject;

    public LabelDto() {
    }

    public LabelDto(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelDto labelDto = (LabelDto) o;
        return Objects.equals(subject, labelDto.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }

    @Override
    public String toString() {
        return "LabelDto{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
