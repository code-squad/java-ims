package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue add(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
