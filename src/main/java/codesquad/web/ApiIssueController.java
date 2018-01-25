package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "issueService")
	private IssueService issueService;
	
	@PutMapping("/{id}/milestones/{mileStoneId}")
	public void setMileStone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long mileStoneId) {
		log.debug("loginUser : {}", loginUser);
		issueService.registerMilestone(loginUser, id, mileStoneId);
	}
	
	@PutMapping("/{id}/users/{userId}")
	public void setAssignedUser(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
		issueService.registerUser(loginUser, id, userId);
	}
	
	@PutMapping("/{id}/labels/{labelId}")
	public void setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
		issueService.registerLabel(loginUser, id, labelId);
	}
	
}
