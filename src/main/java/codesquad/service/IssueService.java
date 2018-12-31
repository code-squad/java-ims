package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue add(User loginUser, Issue issue) {
        issue.writtenBy(loginUser);
        return issueRepository.save(issue);
    }

    public List<Issue> print() {
        return issueRepository.findAll();
    }

    public Issue findByIssueId(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
