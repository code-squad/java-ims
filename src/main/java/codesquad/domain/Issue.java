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

    public String getTitle() {
        return title;
    }

    public Issue setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Issue setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
