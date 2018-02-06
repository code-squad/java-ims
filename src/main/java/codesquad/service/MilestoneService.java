package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MilestoneService {

	@Resource(name = "milestoneRepository")
	private MilestoneRepository milestoneRepository;

	public Milestone add(MilestoneDto milestoneDto) {
		return milestoneRepository.save(milestoneDto.toMilestone());
	}
}
