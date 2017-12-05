package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.IssueRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private IssueRepository issueRepository;

	@GetMapping("")
	public String issueList(Model model) {
		model.addAttribute("issues", issueRepository.findAll());
		return "index";
	}
}
