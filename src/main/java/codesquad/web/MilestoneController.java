package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;

@Controller
@RequestMapping("/milestone")
public class MilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	@Resource(name="milestoneService")
	private MilestoneService milestoneService;
	
	@GetMapping("")
	public String list(Model model) {
		log.debug("milestone controller(list) in.");
		model.addAttribute("milestones", milestoneService.findNotDeleted());
		return "/milestone/list";
	}
	
	@GetMapping("/form")
	public String form() {
		log.debug("milestone controller(form) in.");
		return "/milestone/form";
	}
	
	@PostMapping("/newMilestone")
	public String create(@LoginUser User loginUser, String subject, String startDate, String endDate) {
		log.debug("milestone controller(create) in.");
		Milestone milestone = new Milestone(subject, startDate, endDate);
		milestoneService.add(loginUser, milestone);
		log.debug("add complete.");
		return "redirect:/milestone";
	}
}
