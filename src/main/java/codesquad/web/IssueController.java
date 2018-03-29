package codesquad.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;

@Controller
@RequestMapping("/issue")
public class IssueController {
	@Resource
	private IssueRepository issueRepository;

	@GetMapping("/form")
	public String form() {
		return "/issue/form";
	}

	@PostMapping("/newIssue")
	public String create(String subject, String comment) {
		Issue newIssue = new Issue(subject, comment);
		issueRepository.save(newIssue);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String create(@PathVariable Long id, Model model) {
		Issue issue = issueRepository.findOne(id);
		model.addAttribute(issue);
		return "/issue/show";
	}

}
