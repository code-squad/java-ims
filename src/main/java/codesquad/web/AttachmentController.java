package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@Controller
@RequestMapping("/issues/{id}/attachments")
public class AttachmentController {

    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public String upload(@LoginUser User loginUser, @PathVariable long id, MultipartFile file) throws Exception {
        log.debug("original fil name : {}", file.getOriginalFilename());
        log.debug("content type : {}", file.getContentType());
        issueService.upload(file, loginUser,id);
        return "redirect:/";
    }
}
