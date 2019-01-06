package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/issue")
//public class ApiIssueController {
//
//    @Autowired
//    private IssueService issueService;
//
//    @GetMapping("/{id}")
//    public String updateForm(@LoginUser User loginUser, @PathVariable long id) {
//        Issue updateIssue = issueService.findById(id);
//
//        return "";
//    }
//}
