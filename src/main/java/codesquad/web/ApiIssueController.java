package codesquad.web;

import java.net.URI;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;

@RestController
@RequestMapping("/api/issue")
public class ApiIssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "issueService")
	private IssueService issueService;
	
	@Resource(name = "milestoneService")
	private MilestoneService milestoneService;
	
	@PostMapping("")
	public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody IssueDto issueDto) {
		log.debug("Api Issue Controller (create) in!");
		issueService.add(loginUser, issueDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/"));
		return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
	}
	
	@GetMapping("/{id}")
	public IssueDto show(@PathVariable long id) {
		log.debug("Api Issue Controller (show) in!");
		Issue issue = issueService.findById(id);
		return issue._toIssueDto();
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable long id, @LoginUser User loginUser, @Valid @RequestBody IssueDto updateIssue) {
		log.debug("Api Issue Controller (update) in!");
		try {
			issueService.update(loginUser, id, updateIssue.getComment());
			log.debug("updateIssue is " + issueService.findById(id).toString());
			log.debug("updateIssue subject is " + issueService.findById(id).getSubject());
			log.debug("updateIssue comment is " + issueService.findById(id).getComment());
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug("error : " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@LoginUser User loginUser, @PathVariable long id) {
		log.debug("Api Issue Controller (delete) in!");
		try {
			issueService.delete(loginUser, id);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug("error : " + e.getMessage());
		}
	}
	
	@GetMapping("/{id}/milestones/{milestoneId}")
	public IssueDto registerMilestone(@PathVariable long id, @PathVariable long milestoneId, Model model, @LoginUser User loginUser) {
		log.debug("Api Issue Controller (registerMilestone) in!");
		issueService.registerMilestone(id, milestoneId, loginUser);
		
		IssueDto issueDto = issueService.findById(id)._toIssueDto();
		issueDto.setMilestone(milestoneService.findById(milestoneId));
		return issueDto;
	}
	
	@GetMapping("/{id}/setLabel/{labelId}")
	public IssueDto updateLabel(@PathVariable long id, @PathVariable long labelId, @LoginUser User loginUser) throws UnAuthenticationException {
		log.debug("Api Issue Controller (updateLabel) in!");
		try {
			issueService.updateLabel(loginUser, id, labelId);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug("ERROR!");
			throw new UnAuthenticationException();
		}
		IssueDto issueDto = issueService.findById(id)._toIssueDto();
		log.debug("return issue is : " + issueDto.toString());
		return issueDto;
	}
	
	@GetMapping("/{id}/setAssignee/{userId}")
	public IssueDto setAssignee(@PathVariable long id, @PathVariable long userId, @LoginUser User loginUser) throws UnAuthenticationException {
		log.debug("Api Issue Controller (setAssignee) in!");
		try {
			issueService.makeManager(id, userId, loginUser);
			return issueService.findById(id)._toIssueDto();
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			throw new UnAuthenticationException();
		}
	}
	
	@PostMapping("/{id}/addComment")
	public AnswerDto makeAnswer(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String comment) {
		log.debug("Api Issue Controller (makeAnswer) in!");
		Answer addedAnswer = issueService.addComments(loginUser, id, comment);
		return addedAnswer._toAnswerDto();
	}
}
