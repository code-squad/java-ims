package codesquad.domain.label;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import codesquad.domain.deletehistory.ContentType;
import codesquad.domain.deletehistory.DeleteHistory;
import codesquad.domain.user.User;
import codesquad.domain.issue.Issue;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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

    private boolean deleted = false;

    public Label() {
    }

    public Label(String name, String explanation) {
        this(0L, name, explanation);
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }

    public void update(User loginUser, Label updatedLabel) {
        if (loginUser.isGuestUser()) throw new CannotUpdateException("you can't update this label");
        this.name = updatedLabel.name;
        this.explanation = updatedLabel.explanation;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (loginUser.isGuestUser()) throw new CannotDeleteException("you can't delete this label");
        List<DeleteHistory> histories = new ArrayList<>();
        this.deleted = true;
        histories.add(new DeleteHistory(ContentType.LABEL, this.getId(), loginUser));
        return histories;
    }
}
