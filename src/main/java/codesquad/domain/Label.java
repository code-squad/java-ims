package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Label extends AbstractEntity {

    public Label() {
    }

    public Label(String name) {
        this.name = name;
    }

    public Label(long id, String name) {
        super(id);
        this.name = name;
    }

    @OneToMany(mappedBy = "label")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Issue> issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
    private User writer;

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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void writeBy(User user) {
        this.writer = user;
    }
}
