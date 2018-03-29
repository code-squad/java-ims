package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	@Autowired
	MilestoneService milestoneService;
	
	@Autowired
	IssueService issueService;
	
	@GetMapping("")
	public String show(@LoginUser User loginUser, Model model) {
		model.addAttribute("Milestones", milestoneService.findStoneAll());
		return "/milestone/list";
	}

	@GetMapping("/{milestoneId}/issues/{id}")
	public String addMilestone(@LoginUser User loginUser,  @PathVariable long id, @PathVariable long milestoneId) {
		milestoneService.addMilestone(issueService.findById(id), milestoneId);
		return String.format("redirect:/issues/%d", id);
	}

}
