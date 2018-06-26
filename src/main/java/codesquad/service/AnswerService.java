package codesquad.service;

import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnswerService {
    private static final Logger log =  LoggerFactory.getLogger(AnswerService.class);

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public void add(long issueId, User loginUser, String contents) {
    }
}
