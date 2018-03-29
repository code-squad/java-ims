package codesquad.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;

@Service
public class MilestoneService {
	
	@Resource(name = "milestoneRepository")
	private MilestoneRepository milestoneRepository;
	
	
	public Milestone findByStoneId(Long id){
		return milestoneRepository.findOne(id);
	}
	
	public Iterable<Milestone> findStoneAll(){
		return milestoneRepository.findAll();
	}
	
	public Milestone createMilestone(Milestone milestone) {
		if(milestone.equals(null))
			throw new IllegalStateException();
		return milestoneRepository.save(milestone);
	}
	
	@Transactional
	public void addMilestone(Issue issue, long milestoneId) {
		issue.setMilestone(findByStoneId(milestoneId));
	}

}
