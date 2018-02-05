package codesquad.web;

import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/{issueId}")
	public String show(@PathVariable long issueId,  Model model) {
		model.addAttribute("issue", issueService.findById(issueId));
		return "/issue/show";
	}

	@GetMapping("/form")
	public String form() {
		return "/issue/form";
	}

	@PostMapping("")
	public String create(IssueDto issueDto) throws IllegalArgumentException {
		issueService.add(issueDto);
		return "redirect:/issues";
	}
}
