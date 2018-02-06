package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class MilestoneService {
    private static final Logger log = LoggerFactory.getLogger(MilestoneService.class);
    
    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Milestone add(MilestoneDto milestoneDto) throws Exception {
        log.debug("milestoneDto: {}", milestoneDto);
        log.debug("milestone: {}", milestoneDto.toMilestone());
        return milestoneRepository.save(milestoneDto.toMilestone());
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Optional<Milestone> findById(long id){
        return milestoneRepository.findById(id);
    }

    @Transactional
    public void addMilestoneToIssue(Issue issue, long milestoneId){
        Milestone milestone = findById(milestoneId).get();
        issue.setMilestone(milestone);
        milestone.addToIssue(issue);
    }
}
