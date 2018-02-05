package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    public Issue add(IssueDto issueDto) {
        return issueRepository.save(issueDto.toIssue());
    }

    public List<Issue> findAll() {
        return (List<Issue>) issueRepository.findAll();
    }

    public Issue findById(Long id) {
        return issueRepository.findOne(id);
    }
}
