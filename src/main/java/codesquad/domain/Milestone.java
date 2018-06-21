package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String title;

    public long getId() {
        return super.getId();
    }

    public String getTitle() {
        return title;
    }
}
