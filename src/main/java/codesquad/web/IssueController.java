package codesquad.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import codesquad.validate.IssueValidate;

@Controller
@RequestMapping("/issues")
public class IssueController {

	@Resource(name = "issueService")
	private IssueService issueService;

	@GetMapping("/form")
	public String form() {
		return "issue/form";
	}

	@PostMapping("")
	public String create(@Valid IssueDto issueDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", IssueValidate.of(bindingResult));
			return "/issue/form";
		}
		issueService.add(issueDto);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String showIssue(@PathVariable Long id, Model model) {
		model.addAttribute("issue", issueService.findById(id));
		return "issue/show";
	}

}
