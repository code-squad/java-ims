package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("milestoneService")
public class MilestoneService {

    @Autowired
    MilestoneRepository milestoneRepository;

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Milestone save(User loginUser, MilestoneDto milestoneDto) {
        return milestoneRepository.save(milestoneDto._toEntity().writedBy(loginUser));
    }
}
