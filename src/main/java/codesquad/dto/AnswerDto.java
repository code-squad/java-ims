package codesquad.dto;

public class AnswerDto {

    private String content;

    public AnswerDto() {
    }

    public AnswerDto(String content) {
        this.content = content;
    }

    public AnswerDto setContent(String content) {
        this.content = content;
        return this;
    }

    public String getContent() {
        return content;
    }


}
