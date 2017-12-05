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
		if (!HttpSessionUtil.isLoginSession(session)) {
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
	public String showIssue(@PathVariable int id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		if (isMyIssue(issue, session)) {
			model.addAttribute("myIssue", issue);
		}
		model.addAttribute("issue", issue);
		return "issue/show";
	}

	@RequestMapping("issue/{id}/update")
	public String editForm(@PathVariable int id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		if (!isMyIssue(issue, session)) {
			log.debug("본인이 작성한 issue가 아닙니다.");
			return "redirect:/issue/{id}/show";
		}
		model.addAttribute("issue", issue);
		return "issue/edit";
	}

	@PostMapping("issue/{id}/update")
	public String edit(@PathVariable int id, String subject, String comment) {
		Issue issue = issueRepository.findOne(id);
		issue.update(subject, comment);
		issueRepository.save(issue);
		return "redirect:/issue/{id}/show";
	}

	@RequestMapping("issue/{id}/delete")
	public String delete(@PathVariable int id, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		if (!isMyIssue(issue, session)) {
			log.debug("본인이 작성한 issue가 아닙니다.");
			return "redirect:/issue/{id}/show";
		}
		issueRepository.delete(id);
		return "redirect:/";
	}

	private boolean isMyIssue(Issue issue, HttpSession session) {
		log.debug("issue writer: " + issue.getWriter());
		log.debug("session id: " + HttpSessionUtil.loginSessionUserId(session));
		return issue.isWriter(HttpSessionUtil.loginSessionUserId(session));
	}
}
