package codesquad.dto;

import java.util.Objects;

public class Pair {
    private Long id;
    private String content;

    public Pair() {
    }

    public Pair(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        Pair pair = (Pair) o;
        return Objects.equals(id, pair.id) &&
                Objects.equals(content, pair.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
