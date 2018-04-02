package codesquad.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;

@Service
public class MilestoneService {
	@Resource(name = "milestoneRepository")
	MilestoneRepository milestoneRepository;

	public void create(MilestoneDto milestoneDto) {
		milestoneRepository.save(milestoneDto._toMilestone());
	}

	public Object list() {
		return milestoneRepository.findAll();
	}
}
