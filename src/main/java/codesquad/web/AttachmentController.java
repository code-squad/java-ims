package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.response.UploadFileResponse;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class AttachmentController {
    private static final Logger logger = getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/issues/{id}/uploadFile")
    public UploadFileResponse upload(@LoginUser User loginUser, @PathVariable long id, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = attachmentService.storeFile(id, file, loginUser);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        logger.debug("## upload fileDownloadUri : {}",  fileDownloadUri);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
        //String fileName =

        // TODO MultipartFile로 전달된 데이터를 서버의 특정 디렉토리에 저장하고, DB에 관련 정보를 저장한다.
        //return "redirect:/";
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {

        logger.debug("## download : {}",  fileName);
        Resource resource = attachmentService.loadFileAsResource(fileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

        // TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.

        // pom.xml text 파일을 읽어 응답하는 경우 예시

        // Path path = Paths.get("./pom.xml");
        // PathResource resource = new PathResource(path);

        // HttpHeaders header = new HttpHeaders();
        // headers.setContentType(MediaType.TEXT_XML);
        // headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom,xml");
        // headers.setContentLength(resource.contentLegnth());
        // return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
       // return null;

    }
}
