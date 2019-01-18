package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    public List<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable).getContent();
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Issue add(Issue issue) {
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue create(User loginUser, Issue issue) {
        issue.writeBy(loginUser);
        return add(issue);
    }

    @Transactional
    public void update(User loginUser, long id, Issue updatedIssue) {
        Issue original = findById(id);
        original.update(loginUser, updatedIssue);
    }

    @Transactional
    public void delete(User loginUser, long id) throws CannotDeleteException {
        Issue original = findById(id);
        deleteHistoryService.saveAll(original.delete(loginUser));
    }

    @Transactional
    public Issue addToMilestone(User loginUser, long id, long milestoneId) {
        Issue issue = findById(id);
        Milestone milestone = milestoneService.findById(milestoneId);

        milestone.addIssue(loginUser, issue);
        return issue;
    }

    @Transactional
    public Issue setAssignee(User loginUser, long id, long assigneeId) {
        Issue issue = findById(id);
        User assignee = userService.findById(assigneeId);

        issue.toAssignee(loginUser, assignee);
        return issue;
    }

    @Transactional
    public Issue addLabel(User loginUser, long id, long labelId) {
        Issue issue = findById(id);
        Label label = labelRepository.findById(labelId).orElseThrow(EntityNotFoundException::new);

        issue.addLabel(loginUser, label);
        return issue;
    }

    @Transactional
    public Issue changeOpeningAndClosingStatus(User loginUser, long id) {
        Issue issue = findById(id);
        issue.changeOpeningAndClosingStatus(loginUser);

        return issue;
    }
}
