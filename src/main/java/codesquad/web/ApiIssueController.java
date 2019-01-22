package codesquad.web;

import codesquad.domain.issue.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = LoggerFactory.getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    //TODO: Result 객체의 필요성 생각해보기

    @GetMapping("/{id}/status")
    public Issue changeOpeningAndClosingStatus(@LoginUser User loginUser, @PathVariable long id) {
        return issueService.changeOpeningAndClosingStatus(loginUser, id);
    }

    @GetMapping("/{id}/milestones/{milestoneId}")
    public Issue addToMilestone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        return issueService.addToMilestone(loginUser, id, milestoneId);
    }

    @GetMapping("/{id}/labels/{labelId}")
    public Issue addLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        return issueService.addLabel(loginUser, id, labelId);
    }

    @GetMapping("/{id}/assignees/{assigneeId}")
    public Issue setAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long assigneeId) {
        return issueService.setAssignee(loginUser, id, assigneeId);
    }

    @GetMapping("/{id}")
    public Issue show(@PathVariable long id) {
        return issueService.findById(id);
    }
}
