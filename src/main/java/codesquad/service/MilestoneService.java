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
    private MilestoneRepository milestoneRepo;

    public Milestone create(User user, MilestoneDto milestoneDto) {
        return milestoneRepo.save(milestoneDto._toMilestone());
    }

    public List<Milestone> get() {
        return milestoneRepo.findAll();
    }
}
