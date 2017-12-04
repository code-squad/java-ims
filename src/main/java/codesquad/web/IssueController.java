package codesquad.web;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.Issue;

@Controller
@RequestMapping("/")
public class IssueController {
	private ArrayList<Issue> issues = new ArrayList<Issue>();
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);

	@RequestMapping("")
	public String issueList(Model model) {
		model.addAttribute("issues", issues);
		return "index";
	}

	@RequestMapping("issue/write")
	public String issueForm() {
		return "issue/form";
	}

	@PostMapping("issue/write")
	public String writeIssue(String subject, String comment) {
		issues.add(new Issue(subject, comment));
		log.debug(issues.get(0).toString());
		return "redirect:/";
	}

	@RequestMapping("issue/{id}/show")
	public String showIssue(@PathVariable int id, Model model) {
		model.addAttribute("issue", getIssue(id));
		return "issue/show";
	}

	private Issue getIssue(int id) {
		for (Issue issue : issues) {
			if (issue.getId() == id)
				return issue;
		}
		return null;
	}
}
