package codesquad.dto;

import java.util.Objects;

public class AnswerDto {
    private String contents;
    private String name;
    private String createDate;
    private Long id;

    public AnswerDto() {
    }

    public AnswerDto(String contents) {
        this.contents = contents;
    }

    public AnswerDto(String contents, String name, String createDate) {
        this.contents = contents;
        this.name = name;
        this.createDate = createDate;
    }

    public AnswerDto(String contents, String name, String createDate, Long id) {
        this.contents = contents;
        this.name = name;
        this.createDate = createDate;
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDto answerDto = (AnswerDto) o;
        return Objects.equals(contents, answerDto.contents) &&
                Objects.equals(name, answerDto.name) &&
                Objects.equals(createDate, answerDto.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents, name, createDate);
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                ", createDate='" + createDate + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
