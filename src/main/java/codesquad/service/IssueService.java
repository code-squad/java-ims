package codesquad.service;

import codesquad.CannotShowException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class IssueService {
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    public Issue create(@LoginUser User user, IssueDto issueDto) {
        log.debug("create01");
        Issue issue = issueDto.toIssue(user);
        log.debug("create02");
        return issueRepository.save(issue);
    }

    public Issue findById(Long id) throws CannotShowException {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new NullPointerException("Not exist issue."));
        if (issue.isDeleted()) {
            throw new CannotShowException();
        }
        return issue;
    }

    public Issue update(long id, User updateWriter, IssueDto updateIssueDto) {
        Issue dbIssue = issueRepository.findById(id).orElseThrow(() -> new NullPointerException("Not exist issue."));
        dbIssue.update(updateIssueDto, updateWriter);
        return issueRepository.save(dbIssue);
    }

    public void delete(long id) {
        Optional<Issue> dbIssue = issueRepository.findById(id);
        dbIssue.ifPresent(Issue::deleted);
        dbIssue.ifPresent(x -> issueRepository.save(x));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    @Transactional
    public void setMilestone(Long issueId, Long milestoneId) {
        // exception이 발생할 수 있는 것을 먼저 위에 올릴 수 있다.
        Milestone maybeMilestone = milestoneRepository.findById(milestoneId).orElseThrow( () -> new NullPointerException("not exist milestone."));

        Issue issue = findById(issueId);
        issue.registerMilestone(maybeMilestone);
    }

    @Transactional
    public void setAssignee(Long issueId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NullPointerException("not exist user."));
        findById(issueId).registerAssignee(user);
    }

    @Transactional
    public void setLabel(Long issueId, Long labelId) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new NullPointerException("not exist label."));
        findById(issueId).registerLabel(label);
    }
}
