package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/issues/{id}/attachments")
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private IssueService issueService;

    @GetMapping("/{attachmentId}")
    public ResponseEntity<PathResource> download(@LoginUser User loginUser, @PathVariable long attachmentId) {
        try {
            Attachment attachment = attachmentService.findById(attachmentId);
            Path path = Paths.get(attachment.getFilePath());
            PathResource resource = new PathResource(path);
            logger.debug("ATTACHMENT: {} ",attachment.getFilePath());
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.TEXT_XML);
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getOriginalFileName());
            header.setContentLength(resource.contentLength());
            return new ResponseEntity<>(resource, header, HttpStatus.OK);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }


    @PostMapping("")
    public String upload(@LoginUser User loginUser, @PathVariable long id, MultipartFile file) {
        Attachment added = attachmentService.add(file, loginUser, issueService.findById(id));
        logger.debug("Attachment ID: {}", added.getId());
        return "redirect:/";
    }
}
