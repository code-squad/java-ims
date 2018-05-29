package codesquad.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import codesquad.service.IssueService;

@Controller
public class HomeController {

	@Resource(name="issueService")
	private IssueService issueService;
	
	@GetMapping("")
	public String home(Model model) {
		System.out.println("오냐");
		model.addAttribute("issues", issueService.findAll());
		return "index";
	}
	
}
