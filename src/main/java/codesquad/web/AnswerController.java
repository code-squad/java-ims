package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;

@Controller
@RequestMapping("/answers")
public class AnswerController {
	private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

	
	@Autowired
	AnswerService answerService;
	
	@GetMapping("/{id}/issues/{issueId}")
	public String updateForm(Model model, @PathVariable long id, @PathVariable long issueId) {
		model.addAttribute("Answer", answerService.findById(id));
		model.addAttribute("issueId", issueId);
		return "/issue/answerForm";
	}
	
	@PutMapping("/{id}/issues/{issueId}")
	public String update(@LoginUser User loginUser, @PathVariable long id, @PathVariable long issueId, String contents) {
		log.info("controller in");
		try {
			answerService.update(id, contents, loginUser);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		return String.format("redirect:/issues/%d", issueId);
	}


}
