package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class IssueService {
    private static final Logger logger = LoggerFactory.getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    public Issue addIssue(Issue newIssue) {
        logger.debug("Adding newly created issue...");
        return issueRepository.save(newIssue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .filter(issue -> !issue.isDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }
}
