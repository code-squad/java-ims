package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class MileStoneOpen extends AbstractEntity {

    private boolean open = false;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
