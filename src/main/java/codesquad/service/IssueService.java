package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.repository.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import static org.slf4j.LoggerFactory.getLogger;

@Service("issueService")
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue create(User loginUser, IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue(loginUser));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    public Issue findByIssueId(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueDto updateIssue) {
        return findByIssueId(id).modify(updateIssue, loginUser);
    }

    @Transactional
    public Issue deleteIssue(User loginUSer, long id) {
       return findByIssueId(id).delete(loginUSer);
    }
}
