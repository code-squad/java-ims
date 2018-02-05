package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue add(IssueDto issueDto) {return issueRepository.save(issueDto.toIssue());}

    public Iterable<Issue> findAll() {return issueRepository.findAllByDeleted(false);}

    public Optional<Issue> findById(long id) {return issueRepository.findById(id);}

}
