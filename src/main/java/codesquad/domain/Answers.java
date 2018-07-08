package codesquad.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Answers {
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Answers() {
        answers = new ArrayList<>();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean isEmpty() {
        return answers.isEmpty();
    }

    // TODO indent
    public boolean checkAllWriterSameWith(User writer) {
        for (Answer answer : answers) {
            if (!answer.isOwner(writer)) {
                return false;
            }
        }

        return true;
    }
}