package codesquad.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.web.MilestoneController;

@Service
public class MilestoneService {
	@Resource(name="milestoneRepository")
	private MilestoneRepository milestoneRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	public void add(User loginUser, String subject, String startDate, String endDate) {
		log.debug("milestone service in.");
		Milestone newMilestone = new Milestone(subject, startDate, endDate);
		log.debug("new milestone is " + newMilestone.toString());
		milestoneRepository.save(newMilestone);
		log.debug("repository save complete.");
	}
	
	public Milestone findById(long id) {
		log.debug("milestone service (find by id) in");
		return milestoneRepository.findOne(id);
	}
	
	@Transactional
	public Milestone addIssueThenReturnMilestone(Issue issue, long milestoneId) {
		Milestone milestone = this.findById(milestoneId);
		return milestone.addIssue(issue);
	}
}
