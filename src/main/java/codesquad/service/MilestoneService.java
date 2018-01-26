package codesquad.service;

import java.util.ArrayList;
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

	public MilestoneDto add(MilestoneDto milestoneDto) {
		return milestoneRepository.save(milestoneDto._toMilestone())._toMilestoneDto();
	}
	
	public List<MilestoneDto> findAll() {
		List<MilestoneDto> milestoneDtoList = new ArrayList<>();
		for (Milestone milestone : milestoneRepository.findAll()) {
			milestoneDtoList.add(milestone._toMilestoneDto());
		}
		return milestoneDtoList;
	}
	
	public Milestone findById(long id) {
		return milestoneRepository.findOne(id);
	}
}
