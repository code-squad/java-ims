package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    public Issue create(User loginUser, IssueDto issueDto) {
        Issue issue = issueDto.toIssue();
        issue.writeBy(loginUser);
        return issueRepo.save(issue);
    }

    public IssueDto get(Long id) {
        return issueRepo.findById(id).map(Issue::_toDto).orElseThrow(EntityNotFoundException::new);
    }

    public IssueDto get(User loginUser, Long id) {
        Issue issue = issueRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!issue.isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        return issue._toDto();
    }

}
