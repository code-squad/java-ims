package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Issue save(User loginedUser, Issue issue) {
        issue.writeBy(loginedUser);
        return issueRepository.save(issue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public Issue update(long id, Issue updateIssue) {
        Issue dbIssue = issueRepository.findById(id).orElseThrow(() -> new NullPointerException("Not exist issue."));
        dbIssue.update(updateIssue);
        return issueRepository.save(dbIssue);
    }
}
