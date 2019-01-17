package codesquad.service;

import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueRepository;
import codesquad.domain.issue.answer.Answer;
import codesquad.domain.issue.answer.AnswerRepository;
import codesquad.domain.user.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AnswerService {
    private static final Logger log = getLogger(AnswerService.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private IssueRepository issueRepository;

    public Answer add(User loginUser, Issue issue, String comment) {
        log.debug("answer add!");
        return answerRepository.save(new Answer(loginUser, issue, comment));
    }

    public List<Answer> findByIssueId(long id) {
        return issueRepository.findById(id).orElse(null).getAnswers().getAnswers();
    }
}
