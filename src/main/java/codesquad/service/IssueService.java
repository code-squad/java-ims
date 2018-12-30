package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;


    public void add(IssueDto issueDto) {
        issueRepository.save(issueDto._toIssue());
    }
}
