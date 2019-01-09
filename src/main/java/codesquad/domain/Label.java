package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Label extends AbstractEntity {

    public Label() {
    }

    @OneToMany(mappedBy = "label")
    @OrderBy("id ASC")
    private List<Issue> issue;

    private String name;

    public List<Issue> getIssue() {
        return issue;
    }

    public void setIssue(List<Issue> issue) {
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
