package codesquad.domain;

import codesquad.dto.LabelDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Label extends AbstractEntity {

    @Size(min = 5, max = 20)
    @Column(nullable = false, length = 20)
    @NotBlank
    private String subject;

    @ManyToOne
    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
    private User writer;

    public Label() {

    }

    public Label(String subject, User writer) {
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

    public LabelDto _toLabelDto() {
        return new LabelDto(subject, writer);
    }
}
