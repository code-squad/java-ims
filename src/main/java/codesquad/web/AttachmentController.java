package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.ContentType;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        issueService.upload(file, loginUser, id);
        return "redirect:/";
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<PathResource> download(@PathVariable long attachmentId) throws IOException {
        Attachment attachment = issueService.download(attachmentId);
        PathResource resource = new PathResource(attachment.getPath());
        String name = new String(attachment.getOriginalName().getBytes("UTF-8"), "ISO-8859-1");
        log.debug("파일 경로1 : {}", attachment.getPath());
        log.debug("파일 경로2 : {}", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", name));
        headers.setContentLength(resource.contentLength());
        return new ResponseEntity<PathResource>(resource, headers, HttpStatus.OK);
    }
}
