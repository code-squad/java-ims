package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public Milestone get(Long milestoneId) {
        return milestoneRepo.findById(milestoneId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void addIssue(Long id, Issue issue) {
        milestoneRepo.findById(id).map(milestone -> milestone.addIssue(issue)).orElseThrow(EntityNotFoundException::new);
    }
}
