package codesquad.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.Issue;
import codesquad.model.IssueRepository;
import codesquad.util.HttpSessionUtil;

@Controller
@RequestMapping("issue")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);
	@Autowired
	private IssueRepository issueRepository;

	@GetMapping("write")
	public String issueForm(HttpSession session) {
		if (!HttpSessionUtil.isLoginSession(session)) {
			log.debug("로그인 후 작성해주세요.");
			return "redirect:/";
		}
		return "issue/form";
	}

	@PostMapping
	public String writeIssue(Issue issue, HttpSession session) {
		issue.setWriter(HttpSessionUtil.loginSessionUserId(session));
		issueRepository.save(issue);
		log.debug(issue.toString());
		return "redirect:/";
	}

	@GetMapping("{id}")
	public String showIssue(@PathVariable long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		if (isMyIssue(issue, session)) {
			model.addAttribute("myIssue", issue);
		}
		model.addAttribute("issue", issue);
		return "issue/show";
	}

	@GetMapping("{id}/edit")
	public String editForm(@PathVariable long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		if (!isMyIssue(issue, session)) {
			log.debug("본인이 작성한 issue가 아닙니다.");
			return "redirect:/issue/{id}";
		}
		model.addAttribute("issue", issue);
		return "issue/edit";
	}

	@PutMapping("{id}")
	public String edit(@PathVariable long id, String subject, String comment) {
		Issue issue = issueRepository.findOne(id);
		issue.update(subject, comment);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable long id, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		if (!isMyIssue(issue, session)) {
			log.debug("본인이 작성한 issue가 아닙니다.");
			return "redirect:/issue/{id}";
		}
		issueRepository.delete(id);
		return "redirect:/";
	}

	private boolean isMyIssue(Issue issue, HttpSession session) {
		log.debug("issue writer: {}", issue.getWriter());
		log.debug("session id: {}", HttpSessionUtil.loginSessionUserId(session));
		return issue.isWriter(HttpSessionUtil.loginSessionUserId(session));
	}
}
