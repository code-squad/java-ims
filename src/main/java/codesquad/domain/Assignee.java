package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Assignee {
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_assignee_user"))
    private User user;
}
