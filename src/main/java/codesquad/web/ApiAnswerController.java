package codesquad.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;

@RestController
@RequestMapping("/api/answers")
public class ApiAnswerController {
	private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

	@Autowired
	IssueService issueService;

	@Autowired
	AnswerService answerService;

	@PostMapping("/issues/{id}")
	public Answer create(@LoginUser User loginUser, @PathVariable long id, String contents) {
		Answer answer = new Answer(contents);
		Answer newAnswer = answerService.create(answer, loginUser);
		issueService.addAnswer(id, newAnswer);
		return newAnswer;
	}

	@GetMapping("/{id}")
	public Answer detail(@LoginUser User loginUser, @PathVariable long id) {
		return answerService.findById(id);
	}

	@DeleteMapping("/{id}/issues/{issueId}")
	public Answer delete(@LoginUser User loginUser, @PathVariable long id, @PathVariable long issueId) {
		return answerService.delete(id, loginUser);
	}

}
