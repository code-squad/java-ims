package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Label extends AbstractEntity {
    @Size(min=1, max = 15)
    @Column(length = 15, nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
    private User writer;



    public Label(){}

    public
    Label(User writer, Label label) {
        this.writer = writer;
        this.name = label.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}
