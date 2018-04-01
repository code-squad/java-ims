package codesquad.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import codesquad.dto.IssueDto;
import codesquad.service.IssueService;

@Controller
public class IssueController {

	@Resource(name = "issueService")
	private IssueService issueService;

	@GetMapping("/issues/form")
	public String issueForm() {
		return "/issue/form";
	}

	@PostMapping("/issues")
	public String createIssue(IssueDto issueDto) {
		issueService.add(issueDto);
		return "redirect:/";
	}
}
