package codesquad.web;


import java.net.URI;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Answer> create(@LoginUser User loginUser, @PathVariable Long issueId, @RequestBody String comment) {
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/issues/" + issueId));
		return new ResponseEntity<Answer>(issueService.addAnswer(loginUser, issueId, comment),headers,HttpStatus.CREATED);
	}

	@GetMapping("/{answerId}")
	public ResponseEntity<Result> updateForm(@LoginUser User loginUser, @PathVariable Long answerId) {
			return new ResponseEntity<Result>(issueService.checkAnswerOwner(loginUser, answerId), HttpStatus.OK);
	}
	
	@PutMapping("/{answerId}")
	public ResponseEntity<Result> update(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId, @RequestBody String comment) {
		Result result;
		try {
			issueService.updateAnswer(loginUser, answerId, comment);
			result = Result.success();
		} catch (UnAuthenticationException e) {
			result = Result.fail();
		}
		return new ResponseEntity<Result>(result,HttpStatus.OK);

	}

	@DeleteMapping("/{answerId}")
	public ResponseEntity<Result> delete(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId)throws UnAuthenticationException {
			Result result;
			try {
				issueService.deleteAnswer(loginUser, answerId);
				result = Result.success();
			} catch (UnAuthenticationException e) {
				result = Result.fail();
			}
			return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

}
