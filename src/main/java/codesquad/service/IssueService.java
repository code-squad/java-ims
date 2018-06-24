package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepo;

    public Issue create(User loginUser, IssueDto issueDto) {
        Issue issue = issueDto.toIssue();
        issue.writeBy(loginUser);
        return issueRepo.save(issue);
    }

    public Issue findById(Long id) {
        return issueRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Issue findById(User loginUser, Long id) {
        Issue issue = issueRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!issue.isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        return issue;
    }

    public List<Issue> getAll() {
        return issueRepo.findAll();
    }

    @Transactional
    public Issue update(User loginUser, Long id, IssueDto updateIssueDto) {
        Issue issue = issueRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return issue.update(loginUser, updateIssueDto);
    }

    @Transactional(rollbackFor = CannotDeleteException.class)
    public void delete(User loginUser, Long id) throws CannotDeleteException {
        Issue issue = issueRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        deleteHistoryRepo.save(issue.delete(loginUser));
    }

    @Transactional
    public Issue selectMilestone(Long id, Milestone milestone) {
        return issueRepo.findById(id).map(issue -> issue.selectMilestone(milestone)).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Issue selectAssignee(Long id, User assignee) {
        return issueRepo.findById(id).map(issue -> issue.selectAssignee(assignee)).orElseThrow(EntityNotFoundException::new);
    }
}
