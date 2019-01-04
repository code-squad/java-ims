package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public Milestone saveMilestone(User loginUser, MilestoneDto milestoneDto) {
        return milestoneRepository.save(milestoneDto._toMilestone(loginUser));
    }

    public List<Milestone> findAllMilestone() {
        return milestoneRepository.findAll();
    }
}
