package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Label;
import codesquad.domain.MileStone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MileStoneService;
import codesquad.service.UserService;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "issueService")
	private IssueService issueService;
	
	@PutMapping("/{id}/setMileStone/{mileStoneId}")
	public void setMileStone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long mileStoneId) {
		log.debug("loginUser : {}", loginUser);
		issueService.setMileStone(loginUser, id, mileStoneId);
	}
	
	@PutMapping("/{id}/setAssignedUser/{userId}")
	public void setAssignedUser(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
		issueService.setAssignedUser(loginUser, id, userId);
	}
	
	@PutMapping("/{id}/setLabel/{labelId}")
	public void setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
		issueService.setLabel(loginUser, id, labelId);
	}
	
}
