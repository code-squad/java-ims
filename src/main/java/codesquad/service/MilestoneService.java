package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {
    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public void add(Milestone milestone) {
        milestoneRepository.save(milestone);
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

}
