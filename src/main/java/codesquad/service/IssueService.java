package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;
    public Issue add(User user, IssueDto issueDto) {
        issueDto.writeBy(user);
        return issueRepository.save(issueDto._toIssue());
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    @Transactional
    public void update(User loginUser, long id, IssueDto target) {
        Issue original = issueRepository.findById(id).filter(user -> user.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
        original.update(loginUser, target._toIssue());
    }

    private Issue findById(User loginUser, long id) {
        return issueRepository.findById(id)
                .filter(user -> user.equals(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void delete(User loginUser, long id) {
        Issue original = issueRepository.findById(id).filter(user -> user.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
        original.delete(loginUser);

    }


    public Iterable<Issue> findAllDeleted() {
        return issueRepository.findByDelete(false);
    }


}
