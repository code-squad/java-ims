package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue create(User loginUser, Issue issue) {
        issue.writtenBy(loginUser);
        return issueRepository.save(issue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Transactional
    public Issue update(User loginUser, long id, Issue updateIssue) {
        return findById(id).update(loginUser, updateIssue);
    }
}
