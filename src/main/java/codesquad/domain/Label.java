package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Label extends AbstractEntity {
    @Size(min=1, max = 15)
    @Column(length = 15, nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
    private User writer;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}
