package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = LoggerFactory.getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/{id}")
    public Issue changeOpeningAndClosingStatus(@LoginUser User loginUser, @PathVariable long id) {
        Issue issue = issueService.findById(id);
        issueService.changeOpeningAndClosingStatus(loginUser, id);

        return issue;
    }

}
