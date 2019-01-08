package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Label extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @Size(min = 3)
    @Column(nullable = false)
    @Lob
    private String explanation;

    @OneToMany(mappedBy = "label")
    @OrderBy("id ASC")
    private List<Issue> issues;

    public Label() {
    }

    public Label(String name, String explanation) {
        this(0L,name,explanation);
    }

    public Label(long id, String name, String explanation) {
        super(id);
        this.name = name;
        this.explanation = explanation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
