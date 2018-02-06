package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public void create(LocalDateTime start, LocalDateTime end) {
        milestoneRepository.save(new Milestone(start, end));
    }
}
