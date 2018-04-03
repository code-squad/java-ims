package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import codesquad.domain.IssueRepository;

@Controller
public class HomeController {
	@Autowired
	private IssueRepository issueRepository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("issues", issueRepository.findByDeleted(false));
		return "index";
	}
}
