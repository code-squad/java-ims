package codesquad.web;

import codesquad.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Resource(name = "fileStorageService")
    private FileStorageService fileStorageService;

    @PostMapping
    public String upload(MultipartFile file) {
        log.debug("original file name : {}", file.getOriginalFilename());
        log.debug("contenttype : {}", file.getContentType());

        // TODO MultipartFile로 전달된 데이터를 서버의 특정 디렉토리에 저장하고, DB에 관련 정보를 저장한다.


        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) {
        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.

        // pom.xml text 파일을 읽어 응답하는 경우 예시

        // Path path = Paths.get("./pom.xml");
        // PathResource resource = new PathResource(path);

        // HttpHeaders header = new HttpHeaders();
        // header.setContentType(MediaType.TEXT_XML);
        // header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom.xml");
        // header.setContentLength(resource.contentLength());
        // return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
        return null;
    }
}
