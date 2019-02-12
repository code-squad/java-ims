package codesquad.domain;


import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity {

    @Column(nullable = false)
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Label{" +
                "label='" + label + '\'' +
                '}';
    }
}
