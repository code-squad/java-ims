package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.domain.UriGeneratable;

import javax.persistence.EntityNotFoundException;

@Service
public class IssueService {
    private static final Logger logger = LoggerFactory.getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MilestoneService milestoneService;

    public UriGeneratable addIssue(Issue newIssue) {
        return issueRepository.save(newIssue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .filter(issue -> !issue.isDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public UriGeneratable updateIssue(User loginUser, long id, IssueDto issueDto) {
        Issue target = findById(id);
        return target.update(loginUser, issueDto._toIssue());
    }

    @Transactional
    public void deleteIssue(User loginUser, long id) {
        Issue target = findById(id);
        target.delete(loginUser);
    }

    @Transactional
    public Issue setAssignee(User loginUser, long id, User assignee) {
        Issue target = findById(id);
        return target.setAssignee(loginUser, assignee);
    }

    @Transactional
    public Issue setLabel(User loginUser, long id, long labelId) {
        Issue target = findById(id);
        return target.setLabel(loginUser, labelId);
    }

    @Transactional
    public Issue setMilestone(User loginUser, long id, long milestoneId) {
        Issue target = findById(id);
        Milestone milestone = milestoneService.findById(milestoneId);
        return target.setMilestone(loginUser, milestone);
    }
}
