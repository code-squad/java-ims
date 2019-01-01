package codesquad.service;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneBody;
import codesquad.domain.milestone.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;


    public Milestone create(MilestoneBody milestoneBody) {
        Milestone milestone = new Milestone(milestoneBody);
        return milestoneRepository.save(milestone);
    }

    public Milestone findById(long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
