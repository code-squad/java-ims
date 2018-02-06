package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {
	private static final Logger log = LoggerFactory.getLogger(MilestoneService.class);

	@Resource(name = "milestoneRepository")
	private MilestoneRepository milestoneRepository;

	public Milestone add(MilestoneDto milestoneDto) {
		return milestoneRepository.save(milestoneDto.toMilestone());
	}

	public List<Milestone> findAll() {
		return milestoneRepository.findAll();
	}
}
