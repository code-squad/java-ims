package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class IssueService {
    private static final Logger logger = getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    public Issue add(User loginUser, IssueBody issueBody) {
        Issue issue = new Issue(issueBody);
        issue.writeBy(loginUser);
        return issueRepository.save(issue);
    }

    public Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue findById(User loginUser, long id) {
        return issueRepository.findById(id)
                .filter(x -> x.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueBody target) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);

        issue.update(loginUser, target);
        return issue;
    }

    @Transactional
    public Issue delete(User loginUser, long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);

        issue.delete(loginUser);
        return issue;
    }

    @Transactional
    public void addMilestone(long issuesId, long milestonesId) {
        logger.debug("### addmilestone");
        Issue issue = issueRepository.findById(issuesId)
                .orElseThrow(UnAuthorizedException::new);

        Milestone milestone = milestoneRepository.findById(milestonesId)
                .orElseThrow(UnAuthorizedException::new);
        issue.addMilestone(milestone);
        logger.debug("issue : {}", issue.getMilestone());
    }

    @Transactional
    public void addAssignee(long issuesId, long userId) {
        Issue issue = issueRepository.findById(issuesId)
                .orElseThrow(UnAuthorizedException::new);

        User assignee = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        issue.addAssignee(assignee);
    }

    @Transactional
    public void addLables(long issuesId, long lableId) {
        Issue issue = issueRepository.findById(issuesId)
                .orElseThrow(UnAuthorizedException::new);

        Label label = labelRepository.findById(lableId)
                .orElseThrow(UnAuthorizedException::new);

        issue.addLable(label);
    }
}
