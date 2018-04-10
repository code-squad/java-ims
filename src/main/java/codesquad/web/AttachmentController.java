package codesquad.web;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import codesquad.domain.Attachment;
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

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    IssueService issueService;

    @PostMapping("/issues/{issueId}")
    public String upload(MultipartFile file, @PathVariable long issueId) throws Exception {

        issueService.addAttachment(attachmentService.saveFile(file), issueId);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
        Attachment attachment = attachmentService.findById(id);

        String filePath = new String(attachment.getFilePath().getBytes("UTF-8"));
        String fileName = URLEncoder.encode(attachment.getFileName(), "utf-8");

        PathResource resource = new PathResource(filePath);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_XML);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        header.set(HttpHeaders.CONTENT_ENCODING, "utf-8");
        header.setContentLength(resource.contentLength());
        return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}
