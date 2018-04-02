package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;

@Controller
public class IssueController {

	@Resource(name = "issueService")
	private IssueService issueService;

	@GetMapping("/issues/form")
	public String issueForm(HttpSession session) {
		User writer = (User) session.getAttribute("loginedUser");
		if (writer == null) {
			return "/users/login";
		}
		return "/issue/form";
	}

	@GetMapping("/issues/{id}")
	public String showDetail(@PathVariable long id, Model model) {
		model.addAttribute("issue", issueService.findIssue(id));
		return "/issue/show";
	}

	@PostMapping("/issues")
	public String createIssue(IssueDto issueDto, HttpSession session) {
		User writer = (User) session.getAttribute("loginedUser");
		if (writer == null) {
			return "redirect:/users/login";
		}
		issueDto.setWriter(writer);
		issueService.add(issueDto);
		return "redirect:/";
	}

	@DeleteMapping("/issues/{id}")
	public String delete(@PathVariable long id, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginedUser");
		if (loginUser == null) {
			return "redirect:/users/login";
		}
		issueService.delete(id, loginUser);
		return "redirect:/";
	}
	
	@GetMapping("/issues/{id}/form")
	public String updateForm(@PathVariable long id, HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginedUser");
		if (loginUser == null) {
			return "redirect:/users/login";
		}
		model.addAttribute("issue", issueService.findIssue(id));
		return "/issue/updateForm";
	}

	@PutMapping("/issues/{id}")
	public String update(@PathVariable long id, IssueDto issueDto, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginedUser");
		if (loginUser == null) {
			return "redirect:/users/login";
		}
		issueService.update(id, issueDto, loginUser);
		return "redirect:/";
	}
}
