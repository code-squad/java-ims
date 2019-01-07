package codesquad.domain;

import javax.persistence.*;
import java.util.*;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
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
}
