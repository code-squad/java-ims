package codesquad.service;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Milestone add(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }
}
