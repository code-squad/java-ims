package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Contents;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Issue add(IssueDto issueDto, User loginUser) {
        issueDto.setWriter(loginUser);
        return issueRepository.save(issueDto._toIssue());
    }

    public Issue oneself(User loginUser, long id) {
        return issueRepository.findById(id)
                .filter(issue -> issue.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public Issue update(User loginUser, long id, Contents updateIssueContents) {
        Issue issue = findById(id);
        return issue.update(loginUser, updateIssueContents);
    }

    @Transactional
    public void delete(User loginUser, long id) {
        log.debug("삭제");
        Issue issue = oneself(loginUser, id);
        deleteHistoryService.saveAll(issue.delete(loginUser));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }
}
