package codesquad.web;

import codesquad.domain.AttachmentInfo;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("issues/{issueId}/attachments")

public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Resource(name = "attachmentService")
    private AttachmentService attachmentService;

    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public String upload(@LoginUser User loginUser, @RequestParam("file") MultipartFile file, @PathVariable Long issueId) throws IOException {
        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("content type: {}", file.getContentType());
        Issue issue = issueService.findById(issueId);
        attachmentService.store(file, issue, loginUser);
        return "redirect:/";
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<PathResource> download(@LoginUser User loginUser, @PathVariable long attachmentId) throws Exception {
        AttachmentInfo attachmentInfo = attachmentService.findById(attachmentId);
        log.debug("resource path : {}", attachmentInfo.getFileDirectory() + attachmentInfo.getFileUuid());
        Path path = Paths.get(attachmentInfo.getFileDirectory() + attachmentInfo.getFileUuid());
        PathResource resource = new PathResource(path);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", attachmentInfo.getFileDirectory() + attachmentInfo.getFileUuid()));
        header.setContentLength(resource.contentLength());
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }


}