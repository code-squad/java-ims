package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue add(IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue());
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public IssueDto findById(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new)._toIssueDto();
    }
}
