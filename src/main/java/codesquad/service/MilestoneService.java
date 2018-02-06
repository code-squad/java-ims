package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {

    private static final Logger log = LoggerFactory.getLogger(MilestoneService.class);

    @Autowired
    private MilestoneRepository milestoneRepository;

    public Milestone create(MilestoneDto milestoneDto) {
        return milestoneRepository.save(milestoneDto.toMilestone() );
    }

    public List<Milestone> findAllMileStone() {
        return milestoneRepository.findAll();
    }
}
