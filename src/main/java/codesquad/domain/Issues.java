package codesquad.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Issues {

    /* mapped by 관리자를 넣고 싶은데.. 이 부분은 다같이 사용하길레 공용해서 쓰려다보니 mappedby에 milestone, label 하고 싶음! How??! */
    @OneToMany
    private List<Issue> issues = new ArrayList<>();

    public Issues() {

    }

    public Issues(List<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue, Milestone milestone) {
        issues.add(issue);
        issue.setMilestone(milestone);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
