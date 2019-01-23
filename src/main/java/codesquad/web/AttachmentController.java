package codesquad.web;

import org.slf4j.Logger;
import org.springframework.core.io.FileSystemResource;
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

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
    private static final Logger log = getLogger(AttachmentController.class);

    @PostMapping("")
    public String upload(MultipartFile file) throws IOException {
        log.debug("파일 이름 : {}", file.getOriginalFilename());
        log.debug("contentType : {}", file.getContentType());
        log.debug(String.valueOf(file.getBytes()));
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileSystemResource> download(@PathVariable long id) throws IOException {
        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
        // pom.xml text 파일을 읽어 응답하는 경우 예시

         Path path = Paths.get("./pom.xml");
        FileSystemResource resource = new FileSystemResource(path);

         HttpHeaders header = new HttpHeaders();
         header.setContentType(MediaType.TEXT_XML);
         header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom.xml");
         header.setContentLength(resource.contentLength());
         return new ResponseEntity<FileSystemResource>(resource, header, HttpStatus.OK);
    }
}
