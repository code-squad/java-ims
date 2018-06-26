package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Milestone findById(long id) {
        return milestoneRepository.findById(id)
                .filter(milestone -> !milestone.isDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(MilestoneDto milestoneDto) {
        milestoneRepository.save(milestoneDto._toMilestone());
    }

    @Transactional
    public void add(long milestoneId, Issue issue) {
        Milestone milestone = findById(milestoneId);
        milestone.addIssue(issue);
    }
}
