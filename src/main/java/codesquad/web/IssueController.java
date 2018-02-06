package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);

	@Resource(name = "issueService")
	private IssueService issueService;

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("issues", issueService.findAll());
		return "/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id,  Model model) {
		model.addAttribute("issue", issueService.findById(id));
		return "/issue/show";
	}

	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}
		return "/issue/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, IssueDto issueDto) throws IllegalArgumentException {
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}

		issueService.add(issueDto, loginUser);
		return "redirect:/issues";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}

		model.addAttribute("issue", issueService.findById(id));
		return "/issue/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto target) {
		log.debug("target: {}", target);
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}

		issueService.update(loginUser, id, target);
		return String.format("redirect:/issues/%d", id);
	}

	@DeleteMapping("/{id}/delete")
	public String delete(@LoginUser User loginUser, @PathVariable long id) {
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}

		if (issueService.delete(loginUser, id)) {
			return String.format("redirect:/issues", id);
		}
		return String.format("redirect:/issues", id);
	}
}
