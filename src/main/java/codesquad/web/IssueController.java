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

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;

@Controller
@RequestMapping("/issues")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IssueRepository issueRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";
		}
		return "/issue/form";
	}

	@PostMapping("")
	public String create(HttpSession session, String title, String contents, Model model) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";
		}
		try {
			validCheck(title, contents);
			User sessionUser = HttpSessionUtils.getUserFromSession(session);
			Issue newIssue = new Issue(title, contents, sessionUser);
			log.debug(newIssue.toString());
			issueRepository.save(newIssue);
			return "redirect:/";
		} catch (Exception e) {
			return "/issue/form";
		}
	}

	void validCheck(String title, String contents) throws Exception {
		vaild(title);
		vaild(contents);
	}

	void vaild(String input) throws Exception {
		if (input.isEmpty() && input == "") {
			throw new Exception();
		}
	}
	
	@GetMapping("/{id}")
	public String showIssue(@PathVariable Long id, Model model) {
		model.addAttribute("issue", issueRepository.findOne(id));
		log.debug(issueRepository.findOne(id).toString());
		return "/issue/show";
	}
	
	@GetMapping("/{id}/update")
	public String updateIssueView (@PathVariable Long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		Result result = valid(session, issue);
		if(!result.isValid()) {
			return issueValidCheck(id, model, result);
		}
		model.addAttribute("issue", issue);
		return "/issue/updateForm";		
	}

	private String issueValidCheck(Long id, Model model, Result result) {
		model.addAttribute("issue", issueRepository.findOne(id));
		model.addAttribute("errorMessage", result.getErrorMessage());
		return "/issue/show";
	}
	
	private Result valid(HttpSession session, Issue issue) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!issue.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return Result.ok();
	}
	
	@PutMapping("/{id}")
	public String updateIssue(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		Result result = valid(session, issue);
		if(!result.isValid()) {
			return issueValidCheck(id, model, result);
		}
		issue.update(title, contents);
		issueRepository.save(issue);
		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")
	public String deleteIssue(@PathVariable Long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		Result result = valid(session, issue);
		if(!result.isValid()) {
			return issueValidCheck(id, model, result);
		}
		issueRepository.delete(issue);
		return "redirect:/";
	}
}
