package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.UserService;

@Controller
@RequestMapping("/issues")
public class IssueController {

	@Autowired
	IssueService issueService;
	
	@Autowired
	UserService userService;

	@GetMapping("")
	public String form() {
		return "/issue/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, IssueDto issueDto, HttpSession session) {
		issueService.create(loginUser, issueDto);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable long id, Model model) {
		model.addAttribute("Issue", issueService.findById(id));
		model.addAttribute("Users", userService.findAll());
		model.addAttribute("Labels", issueService.findLabelAll());
		return "/issue/show";
	}

	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto issueDto) {
		issueService.update(loginUser, id, issueDto);
		return String.format("redirect:/issues/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable long id) {
		issueService.delete(loginUser, id);
		return "redirect:/";
	}
	
	@GetMapping("/{id}/milestones/{milestoneId}")
	public String addMilestone(@LoginUser User loginUser,  @PathVariable long id, @PathVariable long milestoneId) {
		issueService.addMilestone(id, milestoneId);
		return String.format("redirect:/issues/%d", id);
	}
	
	@GetMapping("/{id}/assignees/{userId}")
	public String addAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
		issueService.addAssignee(id, userId);
		return String.format("redirect:/issues/%d", id);
	}
	
	@GetMapping("/{id}/labels/{labelId}")
	public String addLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
		issueService.addLabel(id, labelId);
		return String.format("redirect:/issues/%d", id);
	}

}
