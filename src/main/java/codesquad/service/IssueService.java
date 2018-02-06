package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    public Optional<Issue> findIssueById (long id) {
        return Optional.ofNullable(issueRepository.findOne(id));
    }

    public List<Issue> findAllIssues () {
        return issueRepository.findAll();
    }

    public void createIssue(IssueDto issue) {
        Issue issue1 = issueRepository.save(issue.toIssue());
    }

    public void update(long id, User loginUser, IssueDto newIssue) throws UnAuthenticationException {
        Optional<Issue> maybeIssue = findIssueById(id);
        if (maybeIssue.isPresent())
            maybeIssue.get().update(loginUser, newIssue);
    }

    public void delete(long id, User loginUser) throws UnAuthenticationException {
        Optional<Issue> maybeIssue = Optional.ofNullable(issueRepository.findOne(id))
                .filter(i -> loginUser.equals(i.getAuthor()));

        if (maybeIssue.isPresent()) {
            issueRepository.delete(id);
            return;
        }

        throw new UnAuthenticationException();
    }
}
