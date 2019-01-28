package codesquad.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.CommentService;
import com.sun.deploy.net.HttpResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@Controller
@RequestMapping("/issues/{issueId}/attachments")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Resource(name = "attachmentService")
    private AttachmentService attachmentService;

    @Resource(name = "commentService")
    private CommentService commentService;

    @PostMapping("")
    public String upload(@LoginUser User loginUser, @PathVariable long issueId, MultipartFile file) throws Exception {
        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("contenttype: {}", file.getContentType());

        String url = "/Users/zingo/Documents/work";
        String convertedName = UUID.randomUUID().toString();

        Files.copy(file.getInputStream(), Paths.get(url, convertedName), StandardCopyOption.REPLACE_EXISTING);
        commentService
                .uploadAttachment(loginUser, issueId, attachmentService.save(loginUser, Attachment.convertedAttachment(file, convertedName)));

        return "redirect:/issues/{issueId}";
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<FileSystemResource> download(@PathVariable long commentId) throws Exception {
        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
        // pom.xml text 파일을 읽어 응답하는 경우 예시

        Attachment attachment = attachmentService.findById(commentId);
        Path path = Paths.get("/Users/zingo/Documents/work/" + attachment.getConvertedName());
        log.debug("***** path : {}", path.toString());
        FileSystemResource resource = new FileSystemResource(path);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_XML);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="
                        + java.net.URLEncoder.encode(attachment.getOriginalName(), "UTF-8"));
        header.setContentLength(resource.contentLength());

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}