package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    // TODO issue 대신 issueDto로 전달해야 할 것 같다
    public Issue add(Issue issue, User loginUser) {
        issue.writeBy(loginUser);
        return issueRepository.save(issue);
    }

    public List<Issue> getList() {
        return issueRepository.findAll();
    }

    public Issue get(Long id) {
        // TODO id가 중복될 경우?
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    // TODO applyToIssue 대체?
    @Transactional
    public void update(User loginUser, Long id, IssueDto target) {
        Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        issue.update(loginUser, target.toIssue());
    }

    @Transactional
    public void delete(User loginUser, Long id) {
        Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // TODO 삭제시 이슈 유저가 로그인 유저와 같은지..
        if (!issue.isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }

        // TODO 그냥 리턴?
        if (issue.checkAnswerExist() && !issue.checkAllAnswerWriterIsSameWithWriter()) {
            return;
        }

        issueRepository.delete(issue);
    }

    @Transactional
    public Issue setMilestone(Long id, Milestone milestone) {
        Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        issue.milestoneBy(milestone);
        return issue;
    }

    @Transactional
    public Issue setAssignee(Long id, User user) {
        Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        issue.assignTo(user);
        return issue;
    }

    @Transactional
    public Issue setLabel(Long id, Label label) {
        Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        issue.labelBy(label);
        return issue;
    }
}
