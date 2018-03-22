package codesquad.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Issue;
import codesquad.security.HttpSessionUtils;
import codesquad.service.IssueService;

@Controller
@RequestMapping("")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	
	@Autowired
	IssueService issueService;
	
	@GetMapping("")
	public String index(HttpSession session, Model model) {
		Iterable<Issue> issues = issueService.findAll();
		model.addAttribute("Issue", issues);
		return "/index";
	}

}
