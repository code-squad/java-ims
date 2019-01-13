package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Label extends AbstractEntity {
    @Size(min = 2, max = 30)
    @Column(length = 30, nullable = false)
    private String name;

    public Label() {
    }

    public Label(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(Label updatedLabel) {
        this.name = updatedLabel.name;
    }

    public boolean isSame(Label label) {
        return name.equals(label.name);
    }
}
