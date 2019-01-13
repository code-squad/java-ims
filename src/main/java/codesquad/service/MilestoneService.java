package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Milestone create(@LoginUser User user, @Valid Milestone milestone) {
        milestone.writeBy(user);
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    @Transactional
    public Issue setMilestone(User loginUser, long issueId, long id) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(UnAuthorizedException::new);
        Milestone milestone = milestoneRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.setMilestone(milestone);
        return issue;
    }
}
