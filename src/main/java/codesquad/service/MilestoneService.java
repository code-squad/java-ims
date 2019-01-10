package codesquad.service;

import codesquad.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Milestone findById(long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Milestone add(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    @Transactional
    public Milestone create(Milestone milestone) {
        return add(milestone);
    }
}
