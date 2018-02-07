package codesquad.web;


import codesquad.UnAuthorizedException;
import codesquad.domain.Attachment;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
    private static final String DIRECTORY = "src/main/resources/attachment/";

    @Resource(name = "attachmentService")
    private AttachmentService attachmentService;

    @PostMapping("")
    public String upload(MultipartFile file, @LoginUser User loginUser) throws Exception {
        if (loginUser == null) throw new UnAuthorizedException();

        String fileName = file.getOriginalFilename();

        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("contenttype: {}", file.getContentType());

        attachmentService.add(loginUser, fileName, DIRECTORY+fileName);
        file.transferTo(new File(Paths.get(DIRECTORY).toRealPath() + "/"+ fileName));

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
        Attachment attachment = attachmentService.findById(id).get();
        String filePath = attachment.getPath();
        String fileName = attachment.getFileName();
        // pom.xml text 파일을 읽어 응답하는 경우 예시

        Path path = Paths.get(filePath);
        log.debug(path.toString());
        PathResource resource = new PathResource(path);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_XML);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        header.setContentLength(resource.contentLength());
        return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}