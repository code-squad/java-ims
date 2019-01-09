package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Milestone create(@LoginUser User user, @Valid Milestone milestone) {
        milestone.writeBy(user);
        return milestoneRepository.save(milestone);
    }

    public Iterable<Milestone> findAll() {
        return milestoneRepository.findAll();
    }
}
