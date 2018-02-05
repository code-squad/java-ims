package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Issue extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String comment;

    public Issue() {

    }

    public Issue(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }
}
