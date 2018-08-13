package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity {

    @Column
    private String title;

    public Label() {

    }

    public Label(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
