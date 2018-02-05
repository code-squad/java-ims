package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Optional<Issue> findIssueById (long id) {
        return Optional.of(issueRepository.findOne(id));
    }

    public List<Issue> findAllIssues () {
        return issueRepository.findAll();
    }

    public void createIssue(IssueDto issue) {
        issueRepository.save((Issue) issue.toIssue());
    }
}
