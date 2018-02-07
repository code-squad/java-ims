package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilestoneService {
	@Resource(name = "milestoneRepository")
	private MilestoneRepository milestoneRepository;

	public Milestone add(MilestoneDto milestoneDto) {
		return milestoneRepository.save(milestoneDto._toMilestone());
	}

	public List<Milestone> findAll() {
		return milestoneRepository.findAll();
	}

	public Milestone findById(long milestoneId) {
		return milestoneRepository.findOne(milestoneId);
	}
}
