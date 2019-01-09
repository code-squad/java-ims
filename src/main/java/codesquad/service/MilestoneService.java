package codesquad.service;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneRepository;
import codesquad.domain.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    MilestoneRepository milestoneRepository;


    public Milestone add(Milestone milestone, User loginUser) {
        milestone.setWriter(loginUser);
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }
}
