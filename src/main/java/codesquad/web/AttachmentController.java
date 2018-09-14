package codesquad.web;

import codesquad.domain.FileInfo;
import codesquad.service.FileStorageService;
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
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/issues/{issueId}/attachments")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Resource(name = "fileStorageService")
    private FileStorageService fileStorageService;

    @PostMapping
    public String upload(MultipartFile file, @PathVariable Long issueId) {
        log.debug("file name : {}", file.getName());
        log.debug("original file name : {}", file.getOriginalFilename());
        log.debug("contenttype : {}", file.getContentType());

        fileStorageService.store(file, issueId);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws IOException {
        FileInfo fileInfo = fileStorageService.getOne(id);
        PathResource resource = new PathResource(fileInfo.getPath());
        MediaType mediaType = MediaType.valueOf(Files.probeContentType(fileInfo.getPath()));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileInfo.getName());
        header.setContentLength(resource.contentLength());

        return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}
