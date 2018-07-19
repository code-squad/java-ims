package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
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

    public Issue save(User loginedUser, Issue issue) {
        issue.writeBy(loginedUser);
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
        // Java8에서는 ifPresentOrElse를 지원하지 않는다. (Java9이상)
        // 아래와 같이 작성하면 milestone이 존재하지 않을 때 로직처리를 할 수 없다.
//        milestoneRepository.findById(milestoneId).ifPresent(issue::registerMilestone);
        issue.registerMilestone(maybeMilestone);
    }

    @Transactional
    public void setAssignee(Long issueId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NullPointerException("not exist user."));
//        findById(issueId).registerAssignee(userId);
        findById(issueId).registerAssignee(user);
    }

    // TODO 중복저장되지 않게 하기
    @Transactional
    public void setLabel(Long issueId, Long labelId) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new NullPointerException("not exist label."));
        findById(issueId).registerLabel(label);
    }

    public Issue update(long id, Issue updateIssue) {
        Issue dbIssue = issueRepository.findById(id).orElseThrow(() -> new NullPointerException("Not exist issue."));
        dbIssue.update(updateIssue);
        return issueRepository.save(dbIssue);
    }

    public void delete(long id) {
        Optional<Issue> dbIssue = issueRepository.findById(id);
        dbIssue.ifPresent(Issue::deleted);
        dbIssue.ifPresent( x -> issueRepository.save(x));
//        Issue issue = issueRepository.findById(id).get();
//        log.debug("issue deleted : {}", issue.isDeleted());
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }
}
