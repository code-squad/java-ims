package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.DeleteHistoryRepository;
import codesquad.domain.issue.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.domain.issue.IssueBody;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    public Issue create(User loginUser, IssueBody issueBody) {
        Issue issue = Issue.of(issueBody);
        issue.writtenBy(loginUser);
        return issueRepository.save(issue);
    }

    public Issue findById(User loginUser, long id) {
        Issue issue = findById(id);
        if(!issue.isOwner(loginUser)) throw new UnAuthorizedException();
        return issue;
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueBody updateIssueBody) {
        return findById(id).update(loginUser, updateIssueBody);
    }

    @Transactional
    public void deleteIssue(User loginUser, long id) {
        deleteHistoryRepository.save(findById(id).delete(loginUser));
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
