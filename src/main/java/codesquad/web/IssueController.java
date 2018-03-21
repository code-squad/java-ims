package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.dto.IssueDto;
import codesquad.service.IssueService;

@Controller
@RequestMapping("/issues")
public class IssueController {
	
	@Autowired
	IssueService issueService;
	
	@GetMapping("")
	public String form() {
		return "/issue/form";
	}
	
	@PostMapping("")
	public String create(IssueDto issueDto) {
		issueService.create(issueDto);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String detail(@PathVariable long id, Model model) {
		model.addAttribute("Issue", issueService.findById(id));
		return "/issue/show";
	}
	

}
