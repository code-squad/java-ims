package codesquad.domain.label;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Label extends AbstractEntity {
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String label;

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
}
