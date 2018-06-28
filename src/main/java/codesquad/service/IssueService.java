package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("issueService")
public class IssueService {

    private final Logger log = LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "answerService")
    private AnswerService answerService;

    public Issue save(User loginUser, IssueDto issueDto) {
        Issue newIssue = issueDto._toIssue();
        return issueRepository.save(newIssue.writtenBy(loginUser));
    }

    public Issue findById(Long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAllByDeletedOrderByIdDesc(false);
    }

    public Issue findById(User loginUser, Long id) throws UnAuthenticationException {
        return issueRepository.findById(id)
                .filter(issue -> issue.isWriter(loginUser))
                .orElseThrow(UnAuthenticationException::new);
    }

    @Transactional
    public Issue update(User loginUser, Long id, IssueDto target) throws UnAuthenticationException {
        return findById(loginUser, id).update(target);
    }

    @Transactional
    public Issue delete(User loginUser, Long id) throws UnAuthenticationException {
        return findById(loginUser, id).delete();
    }

    public void setMilestone(Long issueId, Milestone milestone) {
        issueRepository.save(findById(issueId).registerMilestone(milestone));
    }

    @Transactional
    public void setAssignee(Long issueId, User assignee) {
        findById(issueId).assignDirector(assignee);
    }

    @Transactional
    public void setLabel(Long issueId, Label label) {
        findById(issueId).assignLabel(label);
    }

    public ImmutableListMultimap<Label, Issue> findByLabel() {
        List<Issue> issues = (List<Issue>) findAll();
        return Multimaps.index(issues, input -> input.getCurrentLabel());
    }

    @Transactional
    public Answer addAnswer(User loginUser, Long issueId, String content) {
        Answer answer = new Answer(content);
        answer.writtenBy(loginUser);
        findById(issueId).addAnswer(answer);
        answer.toIssue(findById(issueId));
        log.debug("made answer : {}", answer);
        log.debug("updated issue : {}", findById(issueId));
        return answerService.save(answer);
    }
}
