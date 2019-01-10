package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.repository.MilestoneRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MilestoneService {
    private static final Logger log = getLogger(MilestoneService.class);

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Milestone create(User loginUser, MilestoneDto milestoneDto) {
       return milestoneRepository.save(milestoneDto._toMilestone(loginUser));
    }

    public Iterable<Milestone> findAll() {
        return milestoneRepository.findAll();
    }
}
