package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {
    private static final Logger log = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue add(IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue());
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }
}
