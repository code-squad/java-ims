package codesquad.web;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("")
	public String create(@LoginUser User loginUser, String title, String startDate, String endDate) throws ParseException {
		log.info("this is controller!!");
		Milestone milestone = new Milestone(title, startDate, endDate);
		Milestone newMilestone = milestoneService.createMilestone(milestone);
		return "redirect:/milestones";
	}
	
	@GetMapping("")
	public String show(@LoginUser User loginUser, Model model) {
		model.addAttribute("Milestones", milestoneService.findStoneAll());
		return "/milestone/list";
	}
	
	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		return "/milestone/form";
	}

	@GetMapping("/{milestoneId}/issues/{id}")
	public String addMilestone(@LoginUser User loginUser,  @PathVariable long id, @PathVariable long milestoneId) {
		milestoneService.addMilestone(issueService.findById(id), milestoneId);
		return String.format("redirect:/issues/%d", id);
	}

}
