package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public Answer createAnswer(User loginUser, Issue issue, Answer answer) {
        issue.addAnswer(answer);
        return answer.applyWriter(loginUser);
    }
}
