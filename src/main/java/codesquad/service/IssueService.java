package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
