package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class IssueService {
    private static final Logger log =  LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue add(User loginUser, IssueDto issueDto) {
        issueDto.toIssue().writeBy(loginUser);
        log.info("add method called");
        log.info("issueDto : {}", issueDto.toString());
        return issueRepository.save(issueDto.toIssue());
    }

    public Issue findById(long id) {
        log.info("findById method called : {}", issueRepository.findById(id).get().toString());
        return issueRepository.findById(id).get();
    }

    @Transactional
    public void update(long id, IssueDto updateIssue) {
        log.info("update method called");
        Issue original = issueRepository.findById(id).get();
        original.update(updateIssue.toIssue());
    }

    @Transactional
    public void  delete(User loginUser, long id) throws CannotDeleteException {
        Issue issue = issueRepository.findById(id).get();
        if (!issue.isOwner(loginUser))
            throw new CannotDeleteException("자신이 쓴 글만 삭제할 수 있습니다.");
        log.info("delete success");
        issueRepository.deleteById(id);
    }
}
