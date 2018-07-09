package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Attachment extends AbstractEntity {
    private String name;
    private String type;

    public Attachment() {
    }

    public Attachment(String name, String type) {
        this.name = name;
        this.type = type;
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
                '}';
    }
}
