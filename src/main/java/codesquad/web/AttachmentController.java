package codesquad.web;

import codesquad.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("issues/{issueId}/attachments")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Resource(name = "attachmentService")
    private AttachmentService attachmentService;

    @PostMapping("")
    public String upload(@RequestParam("file") MultipartFile file, @PathVariable Long issueId) throws IOException {
        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("content type: {}", file.getContentType());

        attachmentService.store(file, issueId);
        return "redirect:/";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
//        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
//        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
//
//        // pom.xml text 파일을 읽어 응답하는 경우 예시
//
//        // Path path = Paths.get("./pom.xml");
//        // PathResource resource = new PathResource(path);
//
//        // HttpHeaders header = new HttpHeaders();
//        // header.setContentType(MediaType.TEXT_XML);
//        // header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom.xml");
//        // header.setContentLength(resource.contentLength());
//        // return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
//    }




}