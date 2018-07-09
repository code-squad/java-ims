package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Attachment extends AbstractEntity {
    private String name;
    private String type;
    private Long issueId;

    public Attachment() {
    }

    public Attachment(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Attachment(String name, String type, Long issueId) {
        this.name = name;
        this.type = type;
        this.issueId = issueId;
    }

    public Attachment(long id, String name, String type, Long issueId) {
        super(id);
        this.name = name;
        this.type = type;
        this.issueId = issueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, type);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", issueId=" + issueId +
                '}';
    }
}
