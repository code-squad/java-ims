package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.User;
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

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

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
    public Issue update(User loginUser, long id, Issue updatedIssue) {
        Issue original = findById(id);
        original.update(loginUser, updatedIssue);
        return add(original);
    }

    @Transactional
    public Issue delete(User loginUser, long id) throws CannotDeleteException {
        Issue original = findById(id);
        deleteHistoryService.saveAll(original.delete(loginUser));
        return add(original);
    }

    @Transactional
    public Milestone addToMilestone(User loginUser, long id, long milestoneId) {
        Issue issue = findById(id);
        Milestone milestone = milestoneService.findById(milestoneId);

        milestone.addIssue(loginUser, issue);
        return milestoneService.add(milestone);
    }
}
