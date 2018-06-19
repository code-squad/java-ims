package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("issueService")
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue addIssue(User writer, Issue issue) {
        issue.writeBy(writer);
        return issueRepository.save(issue);
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Issue checkOwner(long id, User loginUser){
        return issueRepository.findById(id)
                .filter(issue -> issue.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void update(long id, User loginUser, IssueDto target) {
        Issue origin = findById(id);
        origin.update(loginUser, target.toIssue());
    }

    @Transactional
    public void delete(long id, User loginUser) {
        Issue issue = findById(id);
        if (!issue.isOwner(loginUser)){
            throw new UnAuthorizedException();
        }
        issueRepository.deleteById(id);
    }

    @Transactional
    public void setMilestone(long id, User loginUser, Milestone milestone){
        Issue issue = findById(id);
        issue.setMilestone(loginUser, milestone);
    }
}
