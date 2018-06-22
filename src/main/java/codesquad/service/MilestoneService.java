package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.exception.AlreadyAssignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public Milestone findById(Long milestoneId) {
        return milestoneRepository.findById(milestoneId).orElseThrow(EntityNotFoundException::new);
    }

    public void setIssue(Long milestoneId, Issue issue) throws AlreadyAssignException {
        findById(milestoneId).assignIssue(issue);
    }
}
