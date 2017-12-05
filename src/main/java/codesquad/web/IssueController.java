package codesquad.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.Issue;
import codesquad.model.IssueRepository;
import codesquad.util.HttpSessionUtil;

@Controller
@RequestMapping("/")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);
	@Autowired
	private IssueRepository issueRepository;

	@RequestMapping("")
	public String issueList(Model model) {
		model.addAttribute("issues", issueRepository.findAll());
		return "index";
	}

	@RequestMapping("issue/write")
	public String issueForm(HttpSession session) {
		if(!HttpSessionUtil.isLoginSession(session)) {
			log.debug("로그인 후 작성해주세요.");
			return "redirect:/";
		}
		return "issue/form";
	}

	@PostMapping("issue/write")
	public String writeIssue(Issue issue) {
		issueRepository.save(issue);
		log.debug(issue.toString());
		return "redirect:/";
	}

	@RequestMapping("issue/{id}/show")
	public String showIssue(@PathVariable int id, Model model) {
		model.addAttribute("issue", issueRepository.findOne(id));
		return "issue/show";
	}
}
