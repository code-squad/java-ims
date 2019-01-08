package codesquad.domain;

import codesquad.dto.AnswerDto;

import javax.persistence.*;
import java.util.*;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public Answers() {

    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public Answer updateAnswer(Answer originAnswer, Answer updatedAnswer) {
        answers.remove(originAnswer);
        return originAnswer.update(updatedAnswer);
    }

    public List<AnswerDto> obtainAnswerDtos() {
        List<AnswerDto> answerDtos = new ArrayList<>();
        answers.stream().filter(answer -> !answer.isDeleted())
                .forEach(answer -> answerDtos.add(answer._toAnswerDto()));
        return answerDtos;
    }
}
