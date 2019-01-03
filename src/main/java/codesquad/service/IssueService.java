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
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private static final Logger log = LogManager.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;


    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    public List<Issue> findAll() {
        log.debug("findAll");
        return issueRepository.findAll();
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
        issueRepository.findById(id)
                .map(issue -> issue.deleted(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }
}
