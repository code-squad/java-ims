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
import codesquad.model.Label;
import codesquad.model.LabelRepository;
import codesquad.model.Milestone;
import codesquad.model.MilestoneRepository;
import codesquad.model.User;
import codesquad.model.UserRepository;
import codesquad.util.HttpSessionUtil;

@Controller
@RequestMapping("issue")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LabelRepository labelRepository;

	@GetMapping("write")
	public String issueForm(HttpSession session) {
		if (!HttpSessionUtil.isLoginSession(session)) {
			log.debug("로그인 후 작성해주세요.");
			return "redirect:/";
		}
		return "issue/form";
	}

	@PostMapping("write")
	public String writeIssue(Issue issue, HttpSession session) {
		issue.setWriter(HttpSessionUtil.loginSessionUser(session));
		issueRepository.save(issue);
		log.debug(issue.toString());
		return "redirect:/";
	}

	@GetMapping("{id}")
	public String showIssue(@PathVariable long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		model.addAttribute("assignee", userRepository.findAll());
		model.addAttribute("label", labelRepository.findAll());
		model.addAttribute("milestone", milestoneRepository.findAll());
		model.addAttribute("isChecked", getSelectedMark(true));
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
		issue.updateSubject(subject);
		issue.updateComment(comment);
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

	@PutMapping("{id}/milestone/{milestoneId}")
	public String setMilestone(@PathVariable long id, @PathVariable long milestoneId) {
		Issue issue = issueRepository.findOne(id);
		Milestone milestone = milestoneRepository.findOne(milestoneId);
		issue.setMilestone(milestone);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}
	
	@PutMapping("{id}/label/{labelId}")
	public String setLabel(@PathVariable long id, @PathVariable long labelId) {
		Issue issue = issueRepository.findOne(id);
		Label label = labelRepository.findOne(labelId);
		issue.setLabel(label);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}
	
	@PutMapping("{id}/assignee/{assigneeId}")
	public String setAssignee(@PathVariable long id, @PathVariable String assigneeId) {
		Issue issue = issueRepository.findOne(id);
		User assignee = userRepository.findOne(assigneeId);
		issue.setAssignee(assignee);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}

	private boolean isMyIssue(Issue issue, HttpSession session) {
		log.debug("issue writer: {}", issue.getWriter());
		log.debug("session id: {}", HttpSessionUtil.loginSessionUser(session));
		return issue.isWriter(HttpSessionUtil.loginSessionUser(session));
	}

	private String getSelectedMark(boolean isSelected) {
		if (isSelected) {
			return "mdl-button--accent";
		}
		return "";
	}
}
