package codesquad.service;

import codesquad.domain.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepo;


}
