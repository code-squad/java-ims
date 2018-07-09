package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.PathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@Configuration
@PropertySource(value = "classpath:filepath.properties")
@Controller
@RequestMapping(value = "/issues/{issueId}/attachments", produces = "text/plain;charset=utf-8")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
    @Resource(name = "attachmentService")
    AttachmentService attachmentService;
    @Resource(name = "issueService")
    IssueService issueService;
    @Value("${file.path}")
    private String filePath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
        return attachmentService.makeFileResponse(id);
    }

    @PostMapping("")
    public String upload(@LoginUser User loginUser, @PathVariable long issueId, MultipartFile file) throws Exception {
        log.debug("file path : {}", filePath);
        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("contenttype: {}", file.getContentType());

        // MultipartFile로 전달된 데이터를 서버의 특정 디렉토리에 저장하고, DB에 관련 정보를 저장한다.
        // TODO 테스트 어려움 때문에 get방식이 되었는데 객체지향적으로 변경하려면?
        String newName = attachmentService.makeNewName(file.getOriginalFilename());
        File toSaveFile = attachmentService.makeFile(file.getBytes(), newName);
        Attachment attachment = attachmentService.saveFile(toSaveFile, file.getContentType(), issueId);

        issueService.setFile(attachment.getId(), attachment);

        return String.format("redirect:/issues/%d", issueId);
    }
}