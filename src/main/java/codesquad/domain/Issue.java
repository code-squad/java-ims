package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity{

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String title;

    @Size(min = 3)
    @Column(nullable = false)
    private String content;

    public Issue() {

    }

    public Issue(long id, String title, String content) {
        super(id);
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return Objects.equals(getId(), issue.getId()) &&
                Objects.equals(title, issue.title) &&
                Objects.equals(content, issue.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), title, content);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + getId() + '\'' +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
