package codesquad.service;

import codesquad.exception.UnAuthorizedException;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {
    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public void create(Milestone milestone) {
        milestoneRepository.save(milestone);
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Milestone findById(long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);
    }
}