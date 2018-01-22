package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;

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
	
	public Milestone findById(long id) {
		System.out.println("여기서는 잘 뽑히나~~~~" + milestoneRepository.findOne(id));
		return milestoneRepository.findOne(id);
	}
}
