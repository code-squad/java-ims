package codesquad.web.api;

import codesquad.domain.issue.File;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/issues/{issueId}/attachments")
public class ApiAttachmentController {
    private static final Logger log = LoggerFactory.getLogger(ApiAttachmentController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("")
    public ResponseEntity upload(@LoginUser User loginUser, MultipartFile file) throws Exception {
        File uploadedFile = fileService.upload(loginUser, file);
        return new ResponseEntity<File>(uploadedFile, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity download(@LoginUser User loginUser, @PathVariable long id) throws IOException {
        File file = fileService.findFile(id);
        Path path = Paths.get(file.getLocation());
        FileSystemResource resource = new FileSystemResource(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        String encoredFilename = URLEncoder.encode(file.getOriginalName(), "UTF-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + encoredFilename + ";filename*= UTF-8''" + encoredFilename);
        headers.setContentLength(resource.contentLength());
        return new ResponseEntity<FileSystemResource>(resource, headers, HttpStatus.OK);
    }
}
