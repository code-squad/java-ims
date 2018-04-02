package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;

@Controller
@RequestMapping("/issue")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "issueService")
	private IssueService issueService;

	@GetMapping("/form")
	public String form() {
		return "/issue/form";
	}

	@PostMapping("/newIssue")
	public String create(@LoginUser User loginUser, String subject, String comment) {
		IssueDto newIssue = new IssueDto(subject, comment);
		issueService.add(loginUser, newIssue);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		log.debug("start controller");
		log.debug("id is " + id);
		Issue issue = issueService.findById(id);
		log.debug("issue is " + issue.toString());
		
		model.addAttribute(issue);
		return "/issue/show";
	}
	
	@GetMapping("/{id}/updateIssue")
	public String updateForm(@PathVariable Long id, Model model) {
		Issue originIssue = issueService.findById(id);
		model.addAttribute("issue", originIssue);
		return "/issue/issueUpdateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, @LoginUser User loginUser, String comment, Model model) {
		try {
			Issue issue = issueService.update(loginUser, id, comment);
			model.addAttribute(issue);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug("본인의 이슈만 수정할 수 있습니다.");
			model.addAttribute("errorMessage", "권한이 없습니다. 자신의 이슈만 수정 및 삭제가 가능합니다.");
			model.addAttribute("issue", issueService.findById(id));
			return "redirect:/issue/{id}/updateFail";
		}
		return "/issue/show";
	}
	
	@GetMapping("/{id}/updateFail")
	public String updateFail(@PathVariable Long id, Model model) {
		model.addAttribute("errorMessage", "수정 권한이 없습니다.");
		model.addAttribute("issue", issueService.findById(id));
		return "/issue/show";
	}
	
	@DeleteMapping("/{id}/deleteIssue")
	public String delete(@PathVariable Long id, @LoginUser User loginUser) {
		try {
			issueService.delete(loginUser, id);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug("본인의 이슈만 삭제할 수 있습니다.");
			return "redirect:/issue/{id}/updateFail";
		}
		return "redirect:/";
	}
}
