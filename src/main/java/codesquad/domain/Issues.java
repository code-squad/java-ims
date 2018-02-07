package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Embeddable
public class Issues {

    @JsonIgnore
    @OneToMany
    @JoinColumn(name="id")
    @OrderBy("id ASC")
    private List<Issue> issues;

    public Issues() {

    }

    public List<Issue> getIssues() {
        return issues;
    }

    @Override
    public String toString() {
        return "Issues{" +
                "issues=" + issues +
                '}';
    }

    public void add(Issue issue) {
        issues.add(issue);
    }
}
