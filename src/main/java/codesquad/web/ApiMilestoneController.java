package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;

@RestController
@RequestMapping("/api/milestone")
public class ApiMilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	@Autowired
	private MilestoneService milestoneService;
	
	@PostMapping("/newMilestone")
	public Milestone create(@LoginUser User loginUser, String subject, String startDate, String endDate) {
		log.debug("milestone controller(create) in.");
		Milestone milestone = new Milestone(subject, startDate, endDate);
		milestoneService.add(loginUser, milestone);
		log.debug("add complete.");
		return milestone;
	}

}
