package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/issues/{issueId}/attachments")
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

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long id) throws IOException {
        Attachment attachment = attachmentService.findById(id);
        PathResource pathResource = attachmentService.download(loginUser, issueService.findById(issueId), id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.setContentLength(pathResource.contentLength());
        header.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", attachment.getOriginName()));

        return new ResponseEntity<>(pathResource, header, HttpStatus.OK);
    }
}
