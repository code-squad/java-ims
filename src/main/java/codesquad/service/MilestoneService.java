package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class MilestoneService {
    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Iterable<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    @Transactional
    public Milestone add(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }
}
