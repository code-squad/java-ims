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

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MileStoneService;
import codesquad.service.UserService;
import codesquad.validate.IssueValidate;

@Controller
@RequestMapping("/issues")
public class IssueController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "issueService")
	private IssueService issueService;

	@Resource(name ="mileStoneService")
	private MileStoneService mileStoneService;
	
	
	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		return "/issue/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, @Valid IssueDto issueDto, BindingResult bindingResult, Model model) {
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
		if(issue.isOwner(HttpSessionUtils.getUserFromSession(session))) {
			model.addAttribute("owner", issue);
		}
		model.addAttribute("mileStones", mileStoneService.findAll());
		model.addAttribute("users",userService.findAll());
		model.addAttribute("labels", issueService.findAllLabels());
		return "/issue/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
		model.addAttribute("issue", issueService.findById(id));
		return "/issue/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable Long id, @Valid IssueDto issueDto) throws UnAuthenticationException {
		issueService.update(loginUser, id, issueDto);
		return String.format("redirect:/issues/%d", id);

	}
	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
		issueService.delete(loginUser,id);
		return "redirect:/";
	}

	@GetMapping("/{id}/putInMileStone/{mileStoneId}")
	public String putInMileStone(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long mileStoneId) throws UnAuthenticationException {
		issueService.putInMileStone(loginUser, id, mileStoneId);
		return String.format("/issues/%d", id);
	}

	@GetMapping("/{id}/appointAssignee/{userId}")
	public String appointAssignee(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long userId) throws UnAuthenticationException {
		issueService.appointAssignee(loginUser, id, userId);
		return String.format("/issues/%d", id);
	}

	@GetMapping("/{id}/addLabel/{labelId}")
	public String addLabel(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long labelId) throws UnAuthenticationException {
		issueService.addLabel(loginUser, id, labelId);
		return String.format("/issues/%d", id);
	}
	
	@GetMapping("/labels")
	public String showLabels(Model model) {
		model.addAttribute("labels", issueService.findAllLabels());
		return "/label/list";
	}
}
