package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MilestoneService milestoneService;

    @Transactional
    public Issue add(User loginUser, IssueDto issueDto) {
        Issue issue = issueDto.toIssue();
        issue.writeBy(loginUser);
        return issueRepository.save(issue);
    }

    public List<Issue> findAll() {
        return (List<Issue>) issueRepository.findAll();
    }

    public Issue findById(Long id) {
        return issueRepository.findOne(id);
    }

    public Issue findByIdForEdit(User loginUser, Long id) {
        Issue issue = issueRepository.findOne(id);

        if (!issue.isWriteBy(loginUser)){
            throw new UnAuthorizedException("작성자만 수정할 수 있습니다.");
        }

        return issue;
    }

    @Transactional
    public void update(User loginUser, Long id, IssueDto issueDto) {
        Issue issue = issueRepository.findOne(id);

        issue.update(loginUser, issueDto);
    }

    @Transactional
    public void delete(User loginUser, Long id) {
        Issue issue = issueRepository.findOne(id);

        if (!issue.isWriteBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정 또는 삭제할 수 있습니다.");
        }

        issueRepository.delete(issue);
    }

    @Transactional
    public void setMilestone(User loginUser, Long issueId, Long milestoneId) {
        Issue issue = issueRepository.findOne(issueId);
        Milestone milestone = milestoneService.findOne(milestoneId);

        issue.setMilestone(loginUser, milestone);
    }
}
