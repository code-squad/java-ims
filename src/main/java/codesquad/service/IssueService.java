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
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void update(User loginUser, long id, IssueDto updatedIssue) {
        Issue issue = findById(loginUser, id);
        issue.update(loginUser, updatedIssue);
    }

    @Transactional
    public void delete(User loginUser, long id) throws Exception {
        Issue issue = findById(id);
        deleteHistoryService.saveAll(issue.delete(loginUser));
    }

    @Transactional
    public void setMilestone(long issueId, long milestoneId) {
        Issue issue = findById(issueId);
        Milestone milestone = milestoneService.findById(milestoneId);

        milestone.addIssue(issue);
        issue.getMilestone().deleteIssue(issue);
        issue.setMilestone(milestone);
    }

    @Transactional
    public void close(long id) throws RuntimeException {
        findById(id).close();
    }
}
