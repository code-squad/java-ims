package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;

@Controller
@RequestMapping("/issues")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IssueRepository issueRepository;

	@GetMapping("/form")
	public String form() {
		return "/issue/form";
	}

	@PostMapping("")
	public String create(String title, String contents, Model model) {
		try {
			validCheck(title, contents);
			Issue newIssue = new Issue(title, contents);
			log.debug(newIssue.toString());
			issueRepository.save(newIssue);
			return "redirect:/";
		} catch (Exception e) {
			return "/issue/form";
		}
	}

	void validCheck(String title, String contents) throws Exception {
		vaild(title);
		vaild(contents);
	}

	void vaild(String input) throws Exception {
		if (input.isEmpty() && input == "") {
			throw new Exception();
		}
	}
	
	@GetMapping("/{id}")
	public String showDetailPage(@PathVariable Long id, Model model) {
		System.out.println("들어왔나?");
		model.addAttribute("issue", issueRepository.findOne(id));
		log.debug(issueRepository.findOne(id).toString());
		return "/issue/show";
	}
}
