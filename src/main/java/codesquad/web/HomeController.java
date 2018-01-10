package codesquad.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import codesquad.domain.Issue;
import codesquad.service.IssueService;


@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Resource(name = "issueService")
	    private IssueService issueService;
	 
	@GetMapping("/")
	public String goHome(Model model) {
		List<Issue> issues= issueService.findAll();
		log.debug("issues : {}", issues);
		model.addAttribute("issues", issueService.findAll());
		return "index";
	}
}
