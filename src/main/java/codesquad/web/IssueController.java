package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import codesquad.validate.IssueValidate;

@Controller
@RequestMapping("/issues")
public class IssueController {

	@Resource(name = "issueService")
	private IssueService issueService;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";
		}
		return "/issue/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, @Valid IssueDto issueDto, BindingResult bindingResult, Model model) {
		if(loginUser.isGuestUser()) {
			return "/user/login_failed";
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", IssueValidate.of(bindingResult));
			return "/issue/form";
		}
		issueService.add(loginUser, issueDto);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String showIssue(@PathVariable Long id, Model model, HttpSession session) {
		Issue issue = issueService.findById(id);
		model.addAttribute("issue", issue);
		if(issue.isOwner(session)) {
			model.addAttribute("owner", issue);
		}
		return "/issue/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
		if(loginUser.isGuestUser()) {
			return "/user/login_failed";
		}
		
		model.addAttribute("issue", issueService.findById(id));
		return "/issue/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable Long id, @Valid IssueDto issueDto, Model model) {
		if(loginUser.isGuestUser()) {
			return "/user/login_failed";
		}
		
		issueService.update(id, issueDto);
		return String.format("redirect:/issues/%d", id);

	}
	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable Long id) {
		if(loginUser.isGuestUser()) {
			return "/user/login_failed";
		}
		issueService.delete(id);
		return "redirect:/";
	}
}
