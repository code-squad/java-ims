package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.dto.AnswerDto;
import codesquad.repository.AnswerRepository;
import codesquad.repository.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Service("issueService")
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    public Issue create(User loginUser, IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue(loginUser));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    public Issue findByIssueId(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Answer findByAnswerId(long answerId) {
        return answerRepository.findById(answerId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueDto updateIssue) {
        return findByIssueId(id).modify(updateIssue, loginUser);
    }

    @Transactional
    public Issue deleteIssue(User loginUSer, long id) {
        return findByIssueId(id).delete(loginUSer);
    }

    @Transactional
    public Issue close(User loginUser, long id) {
        return findByIssueId(id).close(loginUser);
    }

    @Transactional
    public Issue open(User loginUser, long id) {
        return findByIssueId(id).open(loginUser);
    }

    @Transactional
    public Set<Label> addLabel(User loginUser, long id, Label label) {
        return findByIssueId(id).addLabel(loginUser, label);
    }

    @Transactional
    public Set<User> assignee(User loginUser , long id, User assignee) {
        return findByIssueId(id).addAssignee(loginUser ,assignee);
    }

    @Transactional
    public Answer addAnswer(User loginUser, long id, AnswerDto answerDto) {
        Issue issue = findByIssueId(id);
        Answer answer = answerDto._toAnswer(issue, loginUser);
        issue.addAnswer(answer);
        return answerRepository.save(answer);
    }

    @Transactional
    public Answer modifyAnswer(User loginUser, long answerId, AnswerDto answerDto) {
        return findByAnswerId(answerId).modify(loginUser, answerDto.getComment());
    }

    @Transactional
    public void deleteAnswer(User loginUser, long answerId) {
        findByAnswerId(answerId).delete(loginUser);
    }
}
