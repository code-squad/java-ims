package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    @Autowired
    IssueRepository issueRepository;

    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

}
