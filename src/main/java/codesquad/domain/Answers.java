package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "issue")
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    public Answers() {
    }

    public Answers(Answer answer) {
        answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return answers.stream().filter(answer -> !answer._toAnswerDto().isDeleted()).collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getCountAnswers() {
        return (int)answers.stream().filter(answer -> !answer._toAnswerDto().isDeleted()).count();
    }
}
