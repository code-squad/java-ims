package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepo;

    public Milestone create(MilestoneDto milestoneDto) {
        return milestoneRepo.save(milestoneDto._toMilestone());
    }
}
