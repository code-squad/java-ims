package codesquad.service;

import java.util.List;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;


    public void add(IssueBody issueBody) {
        Issue issue = new Issue(issueBody);
        issueRepository.save(issue);
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    public IssueDto findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new)._toIssueDto();
    }
}
