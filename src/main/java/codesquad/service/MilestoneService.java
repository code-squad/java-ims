package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {
    @Resource(name = "milestoneRepository")
    MilestoneRepository milestoneRepository;

    public Milestone addMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> getMilestones() {
        return milestoneRepository.findAll();
    }

    public Milestone getMilestone(Long milestoneId) {
        return milestoneRepository.getOne(milestoneId);
    }
}

