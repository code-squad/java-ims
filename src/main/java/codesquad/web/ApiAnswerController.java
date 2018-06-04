package codesquad.web;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiAnswerController {

	@Resource(name = "issueService")
	private IssueService issueService;
	
	@PostMapping("")
	public Answer create(@LoginUser User loginUser, @PathVariable Long issueId, String comment){
		return issueService.addAnswer(loginUser, issueId, comment);
	}
	
	@DeleteMapping("/{answerId}")
	public Result delete(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId) {
		try {
			issueService.deleteAnswer(loginUser, answerId);
			return Result.success();
		} catch (UnAuthenticationException e) {
			return Result.fail();
		}
	}
	
}
