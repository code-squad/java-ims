package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Label extends AbstractEntity {
    @Size(min = 2, max = 30)
    @Column(length = 30, nullable = false)
    private String name;

    @ManyToMany
    private List<Issue> issues = new ArrayList<>();

    public Label() {
    }

    public Label(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(Label updatedLabel) {
        this.name = updatedLabel.name;
    }

    public boolean isSame(Label label) {
        return name.equals(label.name);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public int getCountIssues() {
        return issues.size();
    }

    public boolean setIssues(Issue issue) {
        if (issues.contains(issue)) {
            return issues.remove(issue);
        }
        return issues.add(issue);
    }
}
