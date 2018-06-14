package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
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

    public Issue add(IssueDto issueDto) {
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
    public void delete(long id) {
        log.info("delete method called");
        issueRepository.deleteById(id);
    }
}
