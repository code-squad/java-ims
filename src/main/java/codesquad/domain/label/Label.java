package codesquad.domain.label;

import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Label extends AbstractEntity {
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String label;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
    private User writer;

    public Label() {
    }

    public Label(String label) {
        this.label = label;
    }

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
