package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MultipartFileService;
import org.slf4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.naming.CannotProceedException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/attachments")
public class AttatchmentController {
    private static final Logger log = getLogger(AttatchmentController.class);

    @Resource(name = "multipartFileService")
    private MultipartFileService multipartFileService;

    @PostMapping("/{issueId}")
    public String upload(@LoginUser User user, @PathVariable long issueId, MultipartFile file, Model model) throws Exception {
        if (file.isEmpty()) {
            throw new CannotProceedException();
        }
        multipartFileService.add(user, issueId, file);
        return "redirect:/issues/{issueId}";
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileSystemResource> download(@PathVariable long id) throws IOException {
        String saveName = multipartFileService.findPath(id);
        Path path = Paths.get(saveName);
        FileSystemResource resource = new FileSystemResource(path);

        log.debug("~~~~~~~~1!!{}", saveName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        String encoredFilename = URLEncoder.encode(saveName, "UTF-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + encoredFilename + ";filename*= UTF-8''" + encoredFilename);
        headers.setContentLength(resource.contentLength());
        log.debug("!!!@#!@# {}", headers);
        return new ResponseEntity<FileSystemResource>(resource, headers, HttpStatus.OK);
    }
}
