package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class IssueService {
    private static final Logger log = LogManager.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    public Iterable<Issue> findAll() {
        log.debug("findAll");
        return issueRepository.findByDeleted(false);
    }

    public void add(User loginUser, ContentsBody contentsBody) {
        issueRepository.save(Issue.ofBody(loginUser, contentsBody));
    }

    @Transactional
    public Issue update(long id, User loginUser, ContentsBody contentsBody) {
        return issueRepository.findById(id)
                .map(issue -> issue.update(loginUser, contentsBody))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void deleted(long id, User loginUser) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        deleteHistoryService.save(issue.deleted(loginUser));
    }

    @Transactional
    public void updateMilestone(Long id, Long milestoneId) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.toMilestone(milestoneRepository.findById(milestoneId).get());
    }

    @Transactional
    public void updateLabel(Long id, Long labelId) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.toLabel(labelRepository.findById(labelId).get());
    }

    @Transactional
    public void updateAssignee(Long id, Long userId) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.toAssignee(userRepository.findById(userId).get());
    }
}
