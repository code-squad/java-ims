package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.domain.UriGeneratable;

import javax.persistence.EntityNotFoundException;

@Service
public class IssueService {
    private static final Logger logger = LoggerFactory.getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    public UriGeneratable addIssue(Issue newIssue) {
        return issueRepository.save(newIssue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .filter(issue -> !issue.isDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public UriGeneratable updateIssue(User loginUser, long id, IssueDto issueDto) {
        Issue target = findById(id);
        return target.update(loginUser, issueDto._toIssue());
    }

    @Transactional
    public void deleteIssue(User loginUser, long id) {
        Issue target = findById(id);
        target.delete(loginUser);
    }
}
