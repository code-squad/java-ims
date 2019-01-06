package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
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

    @Transactional
    public Issue add(User loginUser, IssueDto issueDto) {
        issueDto.writeBy(loginUser);
        return issueRepository.save(issueDto._toIssue());
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
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
}
