package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {

    private static final Logger logger = getLogger(IssueService.class);

    @Autowired
    private IssueRepository issueRepository;

    public Issue createIssue(IssueDto issueDto) {
        logger.debug("Call Method createIssue()");
        logger.debug("issue Dto {}", issueDto.toString());
        return issueRepository.save(issueDto._toIssue());
    }

    public List<Issue> findAllIssue() {
        return issueRepository.findAll();
    }
}
