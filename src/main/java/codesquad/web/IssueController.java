package codesquad.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Issue;
import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;

@Controller
@RequestMapping("/issues")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "issueService")
	private IssueService issueService;
	
	@Resource(name = "milestoneService")
	private MilestoneService milestoneService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "labelService")
	private LabelService labelService;
	
	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		return "/issue/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, @Valid IssueDto issueDto, BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			return "/issue/form";
		}
		issueDto.addWriter(loginUser);
		log.debug("issue : {}", issueDto);
		issueService.add(issueDto);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String showIssue(@PathVariable Long id, Model model) {
		model.addAttribute("issue", issueService.findById(id)._toIssueDto());
		model.addAttribute("milestones", milestoneService.findAll());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("labels", labelService.findAll());
		log.debug(issueService.findById(id).toString());
		return "/issue/show";
	}
	
	@GetMapping("/{id}/updateForm")
	public String updateIssueView (@LoginUser User loginUser, @PathVariable Long id, Model model) {
		Issue issue = issueService.findById(id);
		Result result = valid(loginUser, issue);
		if(!result.isValid()) {
			return issueValidCheck(id, model, result);
		}
		model.addAttribute("issue", issue._toIssueDto());
		return "/issue/updateForm";		
	}

	private String issueValidCheck(Long id, Model model, Result result) {
		model.addAttribute("issue", issueService.findById(id)._toIssueDto());
		model.addAttribute("errorMessage", result.getErrorMessage());
		return "/issue/show";
	}
	
	private Result valid(User loginUser, Issue issue) {
		if (!issue.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return Result.ok();
	}
	
	@PutMapping("/{id}")
	public String updateIssue(@LoginUser User loginUser, @PathVariable Long id, @Valid IssueDto issueDto, BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			return "/issue/form";
		}
		Issue issue = issueService.findById(id);
//		로그인 유저가 해당하는 id의 글을 수정할 수 있는 권한이 있는지
		Result result = valid(loginUser, issue);
		if(!result.isValid()) {
			return issueValidCheck(id, model, result);
		}
//		위에서 수정 할 수 있음을 확인했으니 들어온 업데이트 정보를 서비스로 내려주면 된다.
		issueService.update(issueDto, id);
		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")
	public String deleteIssue(@LoginUser User loginUser, @PathVariable Long id, Model model) {
		log.debug("deleted issue id {}", id);
		Issue issue = issueService.findById(id);
		Result result = valid(loginUser, issue);
		if(!result.isValid()) {
			return issueValidCheck(id, model, result);
		}
		issueService.delete(id);
		return "redirect:/";
	}
	
	@GetMapping("/{id}/milestone/{milestoneId}")
	public String updateAddMilestone(@PathVariable Long id, @PathVariable Long milestoneId) {
		issueService.addMilestone(id, milestoneService.findById(milestoneId));
		return "/issue/show";
	}
	
	@GetMapping("/{id}/label/{labelId}")
	public String updateAddLabel(@PathVariable Long id, @PathVariable Long labelId) {
		issueService.addLabel(id, labelService.findById(labelId));
		return "/issue/show";
	}
	
	@GetMapping("/{id}/user/{userId}")
	public String updateAddUser(@PathVariable Long id, @PathVariable Long userId) {
		issueService.addUser(id, userService.findById(userId));
		return "/issue/show";
	}
}
