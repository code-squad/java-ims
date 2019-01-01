package codesquad.service;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneBody;
import codesquad.domain.milestone.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;


    public Milestone create(MilestoneBody milestoneBody) {
        Milestone milestone = new Milestone(milestoneBody);
        return milestoneRepository.save(milestone);
    }
}
