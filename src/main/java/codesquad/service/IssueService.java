package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service("issueService")
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue save(IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue());
    }

    public IssueDto findById(Long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new)._toIssueDto();
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAllByOrderByIdDesc();
    }
}
