package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    @Transactional
    public void add(MilestoneDto milestoneDto) {
        milestoneRepository.save(milestoneDto._toMilestone());
    }
}
