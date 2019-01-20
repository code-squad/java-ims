package codesquad.domain.issue.answer;

import codesquad.domain.DeleteHistory;
import codesquad.domain.user.User;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Answers {
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);
    }

    public long otherAnswerCount(User writer) {
        return answers.stream()
                .filter(answer -> !answer.isOwner(writer))
                .filter(answer -> !answer.isDeleted())
                .count();
    }

    public List<DeleteHistory> delete(User loginUser) {
        List<DeleteHistory> histories = new ArrayList<>();
        for (Answer answer : answers) {
            histories.add(answer.delete(loginUser));
        }
        return histories;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
