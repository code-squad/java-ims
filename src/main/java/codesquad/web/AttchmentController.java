package codesquad.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import codesquad.service.AttchmentService;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
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
@RequestMapping("/attachments")
public class AttchmentController {
    public static final Logger logger = LoggerFactory.getLogger(AttchmentController.class);

    @Resource(name = "attchmentService")
    private AttchmentService attchmentService;

    @PostMapping("")
    public String upload(MultipartFile file) throws Exception {
        logger.debug("original file name : {}", file.getOriginalFilename());
        logger.debug("contenttype : {}", file.getContentType());
        attchmentService.add(file);
        return "redirect:/";
    }

    @GetMapping("{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.

        // pom.xml text 파일을 읽어 응답하는 경우 예시

         Path path = Paths.get("./pom.xml");
         PathResource resource = new PathResource(path);
         HttpHeaders header = new HttpHeaders();
         header.setContentType(MediaType.TEXT_XML);
         header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom.xml");
         header.setContentLength(resource.contentLength());
         logger.info("header info : {}", header.toString());
         return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}
