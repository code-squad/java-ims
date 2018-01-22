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
	
	@Resource(name = "mileStoneService")
	private MileStoneService mileStoneService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "labelService")
	private LabelService labelService;
	
	@PutMapping("/{id}/setMileStone/{mileStoneId}")
	public void setMileStone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long mileStoneId) {
		MileStone mileStone = mileStoneService.findById(mileStoneId);
		log.debug("loginUser : {}", loginUser);
		issueService.setMileStone(loginUser, issueService.findById(id), mileStone);
	}
	
	@PutMapping("/{id}/setAssignedUser/{userId}")
	public void setAssignedUser(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
		User assignedUser = userService.findById(userId);
		issueService.setAssignedUser(loginUser, issueService.findById(id), assignedUser);
	}
	
	@PutMapping("/{id}/setLabel/{labelId}")
	public void setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
		issueService.setLabel(loginUser, id, labelId);
	}
	
}
