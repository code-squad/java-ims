package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/issues/{issueId}/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private IssueService issueService;

    @PostMapping
    public String upload(@LoginUser User loginUser, @PathVariable Long issueId, MultipartFile file) throws IOException {
        Issue issue = attachmentService.upload(loginUser, issueService.findById(issueId), file);
        return issue.generateRedirectUri();
    }
}
