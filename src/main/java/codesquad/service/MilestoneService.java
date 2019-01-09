package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class MilestoneService {
    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Iterable<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Milestone findById(long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Milestone add(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }
}
