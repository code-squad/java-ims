package codesquad.dto;

import codesquad.domain.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswersDto {
    private List<AnswerDto> answers = new ArrayList<>();

    public AnswersDto() {
    }

    public AnswersDto(List<Answer> answers) {
        for (Answer answer : answers) {
            addAnswer(answer);
        }
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(new AnswerDto(answer.getContents(), answer.getWriter().getName(),
                answer.getFormattedCreateDate(), answer.getId()));
    }

    @Override
    public String toString() {
        return "AnswersDto{" +
                "answers=" + answers +
                '}';
    }
}
