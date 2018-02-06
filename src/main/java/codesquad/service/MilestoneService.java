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

    public Milestone add(User loginUser, MilestoneDto milestoneDto) {
        Milestone milestone = milestoneDto.toMilestone();
        milestone.writeBy(loginUser);

        return milestoneRepository.save(milestone);
    }

    public List<Milestone> findAll() {
        return (List<Milestone>) milestoneRepository.findAll();
    }
}
