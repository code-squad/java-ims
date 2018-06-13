package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    public Issue create(User user, IssueDto issueDto) {
        Issue issue = issueDto.toIssue();
        issue.writeBy(user);
        return issueRepo.save(issue);
    }
}
