package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {
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
    public void addToMilestone(User loginUser, long id, long milestoneId) {
        Issue issue = findById(id);
        Milestone milestone = milestoneService.findById(milestoneId);

        milestone.addIssue(loginUser, issue);
    }

    @Transactional
    public void setAssignee(User loginUser, long id, long assigneeId) {
        Issue issue = findById(id);
        User assignee = userService.findById(assigneeId);

        issue.assignedBy(loginUser, assignee);
    }

    @Transactional
    public void addLabel(User loginUser, long id, long labelId) {
        Issue issue = findById(id);
        Label label = labelRepository.findById(labelId).orElseThrow(EntityNotFoundException::new);

        issue.addLabel(loginUser, label);
    }
}
