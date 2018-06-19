package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MilestoneService {

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    public Milestone create(MilestoneDto milestoneDto){
        return milestoneRepository.save(milestoneDto.toMilestone());
    }

    public Milestone findById(long id){
        return milestoneRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Milestone> findAll(){
        return milestoneRepository.findAll();
    }
}
