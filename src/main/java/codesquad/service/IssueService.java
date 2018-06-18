package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service("issueService")
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue save(User loginUser, IssueDto issueDto) {
        Issue newIssue = issueDto._toIssue();
        return issueRepository.save(newIssue.writeBy(loginUser));
    }

    public Issue findById(Long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAllByOrderByIdDesc();
    }

    public Issue findById(User loginUser, Long id) throws UnAuthenticationException {
        return issueRepository.findById(id)
                .filter(issue -> issue.isWriter(loginUser))
                .orElseThrow(UnAuthenticationException::new);
    }
}
