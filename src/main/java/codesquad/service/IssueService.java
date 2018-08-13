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
        dbIssue.ifPresent(x -> issueRepository.save(x));
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    @Transactional
    public Issue setMilestone(User user, Long issueId, Long milestoneId) {
        Issue issue = findById(issueId);
        // Java8에서는 ifPresentOrElse를 지원하지 않는다. (Java9이상)
        // 아래와 같이 작성하면 milestone이 존재하지 않을 때 로직처리를 할 수 없다.
//        milestoneRepository.findById(milestoneId).ifPresent(issue::registerMilestone);

        Optional<Milestone> maybeMilestone = milestoneRepository.findById(milestoneId);
        if (!maybeMilestone.isPresent()) {
            throw new NullPointerException("Milestone is not exist!");
        }
        issue.registerMilestone(maybeMilestone.get());

        return issue;
    }

    @Transactional
    public Issue setAssignee(Long issueId, Long userId) {
        Issue issue = findById(issueId);
        Optional<User> maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            issue.registerAssignee(userId);
        }
        return issue;
    }

    @Transactional
    public Issue setLabel(Long issueId, Long labelId) {
        Issue issue = findById(issueId);
        Optional<Label> maybeLabel = labelRepository.findById(labelId);
        if (maybeLabel.isPresent()) {
            issue.registerLabel(maybeLabel.get());
        }
        return issue;
    }
}
