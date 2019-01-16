package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Label extends AbstractEntity {
    @Size(min = 2, max = 30)
    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
    private User writer;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    public Label() {
    }

    public Label(long id, String name, User writer) {
        super(id);
        this.name = name;
        this.writer = writer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(Label updatedLabel) throws CannotUpdateException{
        for (Issue issue : issues) if (!isOwner(issue.getWriter())) throw new CannotUpdateException("You can't update this label");
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean delete() throws CannotDeleteException{
//        if (issues.contains(null)) return true;
        for (Issue issue : issues) if (!isOwner(issue.getWriter())) throw new CannotDeleteException("You can't delete this label");
        return true;
    }
}
