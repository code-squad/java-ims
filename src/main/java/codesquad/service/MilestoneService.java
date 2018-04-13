package codesquad.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.web.MilestoneController;

@Service
public class MilestoneService {
	@Resource(name="milestoneRepository")
	private MilestoneRepository milestoneRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	public void add(User loginUser, Milestone milestone) {
		log.debug("milestone service in.");
		log.debug("new milestone is " + milestone.toString());
		milestoneRepository.save(milestone);
		log.debug("repository save complete.");
	}
	
	public Milestone findById(long id) {
		log.debug("milestone service (find by id) in");
		return milestoneRepository.findOne(id);
	}
	
	public  Iterable<Milestone> findNotDeleted() {
		return  milestoneRepository.findByDeleted(false);
	}
}
