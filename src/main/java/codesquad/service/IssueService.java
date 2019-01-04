package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {

    private static final Logger logger = getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    @Value("${error.not.oneself}")
    private String oneSelfErrorMessage;

    public Issue createIssue(User loginUser, IssueDto issueDto) {
        logger.debug("Call Method createIssue() : {}", issueDto.toString());
        return issueRepository.save(issueDto._toIssue(loginUser));
    }

    public List<Issue> findAllIssue() {
        List<Issue> issues = issueRepository.findByDeleted(false);
        return issueRepository.findByDeleted(false);
    }

    @Transactional
    public void deleteIssue(User loginUser, Long id) throws UnAuthenticationException {
        logger.debug("Call delete Method LoginUser : {}, id : {}", loginUser.toString(), id);
        Issue issue = issueRepository.findById(id).orElse(null);
        confirmOneSelf(loginUser, id);
        issue.setDeleted(true);
    }

    public IssueDto findIssue(Long id) {
        return issueRepository.findById(id).orElse(null)._toIssueDto();
    }

    public void confirmOneSelf(User loginUser, Long id) throws UnAuthenticationException {
        if(!issueRepository.findById(id).orElse(null).isOneSelf(loginUser)) {
            logger.debug("Error : {}",oneSelfErrorMessage);
            throw new UnAuthenticationException(oneSelfErrorMessage);
        }
    }

    public Issue updateIssue(User loginUser, IssueDto issueDto, Long id) throws UnAuthenticationException {
        logger.debug("Call updateIssue");
        confirmOneSelf(loginUser, id);
        Issue issue = issueRepository.findById(id).orElse(null).update(issueDto._toIssue());
        logger.debug("Issue : {}", issue.toString());
        return issueRepository.save(issue);
    }
}
