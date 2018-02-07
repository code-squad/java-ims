package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.service.AttachmentService;
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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("")
    public String upload(MultipartFile file) throws Exception {
        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("contenttype: {}", file.getContentType());

        String filepath = attachmentService.getFilePath(file.getOriginalFilename());
        File localFile = new File(filepath);
        file.transferTo(localFile);

        attachmentService.saveOneAttachment(localFile.getName(), localFile.getAbsolutePath());
        log.debug("upload log: {} ", localFile.getAbsolutePath());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
        Attachment attachment = attachmentService.findOneAttachment(id);

        Path path = Paths.get(attachment.getFilepath());
        PathResource resource = new PathResource(path);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_XML);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        header.setContentLength(resource.contentLength());

        return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}
