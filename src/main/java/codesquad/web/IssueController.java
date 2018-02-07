package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);

	@Resource(name = "issueService")
	private IssueService issueService;

	@Resource(name = "milestoneService")
	private MilestoneService milestoneService;

	@GetMapping("/form")
	public String form(@LoginUser User user) {
		return "/issue/form";
	}

	@PostMapping("")
	public String create(@LoginUser User user, IssueDto issueDto) {
		issueService.add(user, issueDto.toIssue());
		return "redirect:/";
	}

	@GetMapping("")
	public String showAll(Model model) {
		model.addAttribute("issues", issueService.findAll());
		return "index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		model.addAttribute("issue", issueService.findById(id));
		model.addAttribute("milestones", milestoneService.findAll());
		return "issue/show";
	}

	@PutMapping("/{id}")
	public String update(@LoginUser User user, @PathVariable long id, IssueDto updateIssueDto) {
		issueService.update(user, id, updateIssueDto);
		return "redirect:/";
	}

	@DeleteMapping("/{id}")
	public String delete(@LoginUser User user, @PathVariable long id) {
		issueService.delete(user, id);
		return "redirect:/";
	}

	@PostMapping("/{id}/milestones/{milestoneId}")
	public String insertMilestone(@PathVariable long id, @PathVariable long milestoneId) {
		issueService.addMilestone(milestoneId, id);
		return "redirect:/issues/{id}";
	}

}
