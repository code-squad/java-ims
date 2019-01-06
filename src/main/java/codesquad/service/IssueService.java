package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.DeleteHistoryRepository;
import codesquad.domain.issue.IssueRepository;
import codesquad.domain.User;
import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueBody;
import codesquad.domain.label.Label;
import codesquad.domain.milestone.Milestone;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    public Issue create(User loginUser, IssueBody issueBody) {
        Issue issue = new Issue(issueBody);
        issue.writtenBy(loginUser);
        return issueRepository.save(issue);
    }

    public Issue findById(User loginUser, long id) {
        Issue issue = findById(id);
        if(!issue.isOwner(loginUser)) throw new UnAuthorizedException();
        return issue;
    }

    public Iterable<Issue> findIssueAll() {
        return issueRepository.findByDeleted(false);
    }

    public Iterable<Milestone> findMilestoneAll() {return milestoneService.findAll(); }

    public Iterable<User> findUserAll() {return userService.findAll(); }

    public Iterable<Label> findLabelAll() {
        return labelService.findAll();
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueBody updateIssueBody) {
        return findById(id).update(loginUser, updateIssueBody);
    }

    @Transactional
    public void deleteIssue(User loginUser, long id) {
        deleteHistoryRepository.save(findById(id).delete(loginUser));
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Issue setMilestone(User loginUser, long issueId, long milestoneId) {
        Milestone milestone = milestoneService.findById(milestoneId);
        return findById(issueId).setMilestone(loginUser, milestone);
    }

    @Transactional
    public Issue setAssignee(User loginUser, long issueId, long assigneeId) {
        User assignee = userService.findById(assigneeId);
        return findById(issueId).setAssignee(loginUser, assignee);
    }

    @Transactional
    public Issue setLabel(User loginUser, long issueId, long labelId) {
        Label label = labelService.findById(labelId);
        return findById(issueId).setLabel(loginUser, label);
    }
}
