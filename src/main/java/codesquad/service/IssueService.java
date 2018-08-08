package codesquad.service;

import codesquad.CannotShowException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class IssueService {
    private static final Logger log =  LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    public Issue create(User loginedUser, IssueDto issueDto) {
        Issue issue = issueDto.toIssue(loginedUser);
        return issueRepository.save(issue);
    }

    public Issue findById(long id) throws CannotShowException {
        Issue issue = issueRepository.findById(id).orElseThrow(NullPointerException::new);
        if (issue.isDeleted()) {
            throw new CannotShowException();
        }
        return issue;
    }

    public Issue update(long id, User updateWriter, IssueDto updateIssueDto) {
        Issue dbIssue = issueRepository.findById(id).orElseThrow(() -> new NullPointerException("Not exist issue."));
//        Issue updateIssue = updateIssueDto.toIssue(writer);
        dbIssue.update(updateIssueDto, updateWriter);
        return issueRepository.save(dbIssue);
    }

    public void delete(long id) {
        Optional<Issue> dbIssue = issueRepository.findById(id);
        dbIssue.ifPresent(Issue::deleted);
        dbIssue.ifPresent( x -> issueRepository.save(x));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    public Issue setMilestone(User user, Long issueId, Long milestoneId) {
        Issue issue = findById(issueId);
        milestoneRepository.findById(milestoneId).ifPresent(m -> issue.registerMilestone(m));
        return issue;
    }
}
