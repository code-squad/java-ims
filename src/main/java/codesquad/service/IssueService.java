package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class IssueService {
    private static final Logger log = LogManager.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    public Iterable<Issue> findAll() {
        log.debug("findAll");
        return issueRepository.findByDeleted(false);
    }

    public void add(User loginUser, IssueBody issueBody) {
        issueRepository.save(Issue.ofBody(loginUser, issueBody));
    }

    @Transactional
    public Issue update(long id, User loginUser, IssueBody issueBody) {
        return issueRepository.findById(id)
                .map(issue -> issue.update(loginUser, issueBody))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void deleted(long id, User loginUser) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        deleteHistoryService.save(issue.deleted(loginUser));
    }
}
