package codesquad.web;

import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/issue/{id}/assignees")
public class ApiAssigneeController {
    private static final Logger log = getLogger(ApiAssigneeController.class);

    @Autowired
    IssueService issueService;

    @Autowired
    UserService userService;


    @PostMapping("/{assigneesId}")
    public ResponseEntity<Void> register(@LoginUser User loginUser, @PathVariable long id, @PathVariable long assigneesId) {
        log.debug("이슈에 담당자 적용");
        log.debug("담당자 아이디 : " + assigneesId);
        issueService.registerAssignee(userService.findById(assigneesId), id, loginUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
