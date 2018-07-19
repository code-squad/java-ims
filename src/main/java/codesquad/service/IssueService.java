package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class IssueService {
    private static final Logger log =  LoggerFactory.getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    public Issue save(User loginedUser, Issue issue) {
        issue.writeBy(loginedUser);
        return issueRepository.save(issue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public Issue update(long id, User writer, Issue updateIssue) {
        Issue dbIssue = issueRepository.findById(id).orElseThrow(() -> new NullPointerException("Not exist issue."));
        updateIssue.writeBy(writer);
        dbIssue.update(updateIssue);
        return issueRepository.save(dbIssue);
    }

    public void delete(long id) {
        Optional<Issue> dbIssue = issueRepository.findById(id);
        dbIssue.ifPresent(Issue::deleted);
        dbIssue.ifPresent( x -> issueRepository.save(x));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }
}
