package codesquad.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import codesquad.service.IssueService;

@Controller
public class HomeController {
	@Resource(name = "issueService")
	private IssueService issueservice ;
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("issues", issueservice.findAll());
		return "index";
	}
}
