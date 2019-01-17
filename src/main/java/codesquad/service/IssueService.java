package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Transactional
    public Issue add(User loginUser, IssueDto issueDto) {
        issueDto.writeBy(loginUser);
        return issueRepository.save(issueDto._toIssue());
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Issue findById(User loginUser, long id) {
        return issueRepository.findById(id)
                .filter(user -> user.isOwner(loginUser))
                .orElseThrow(() -> new UnAuthorizedException("You're Unauthorized User"));
    }

    @Transactional
    public void update(User loginUser, long id, IssueDto updatedIssue) {
        Issue issue = findById(loginUser, id);
        issue.update(loginUser, updatedIssue);
    }

    @Transactional
    public void delete(User loginUser, long id) throws Exception {
        Issue issue = findById(loginUser, id);
        deleteHistoryService.saveAll(issue.delete(loginUser));
    }

    @Transactional
    public void setMilestone(User loginUser, long issueId, long milestoneId) {
        milestoneService.findById(milestoneId).addIssue(findById(loginUser, issueId));
    }

    @Transactional
    public void close(User loginUser, long id) throws Exception {
        findById(id).close(loginUser);
    }

    @Transactional
    public void setAssignee(User loginUser, long issueId, long assigneeId) {
        findById(loginUser, issueId).setAssignee(userService.findById(assigneeId));
    }

    @Transactional
    public void setLabel(User loginUser, long issueId, long labelId) {
        findById(loginUser, issueId).setLabel(labelService.findById(labelId));
    }
}
