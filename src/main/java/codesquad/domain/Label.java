package codesquad.domain;

import support.domain.AbstractEntity;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity {
    private String name;

    public Label() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
