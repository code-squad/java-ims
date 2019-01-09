package codesquad.service;

import java.util.List;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public void add(User loginUser, IssueBody issueBody) {
        Issue issue = new Issue(issueBody);
        issue.writeBy(loginUser);
        issueRepository.save(issue);
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    public IssueDto findById(User loginUser, long id) {
        return issueRepository.findById(id)
                .filter(x -> x.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new)._toIssueDto();
    }

    public IssueDto findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new)._toIssueDto();
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueBody target) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);

        issue.update(loginUser, target);
        return issue;
    }
}
