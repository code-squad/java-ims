package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Configuration
@PropertySource(value = "classpath:filepath.properties")
@Controller
@RequestMapping(value = "/attachments", produces = "text/plain;charset=utf-8")
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Value("${file.path}")
    private String filePath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @GetMapping("/{id}")
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
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @PostMapping("")
    public String upload(MultipartFile file) throws Exception {
        log.debug("file path : {}", filePath);

        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("original file size: {}", file.getSize());
        log.debug("contenttype: {}", file.getContentType());

        // TODO MultipartFile로 전달된 데이터를 서버의 특정 디렉토리에 저장하고, DB에 관련 정보를 저장한다.

        String originalFileName = file.getOriginalFilename();

        String savedFileName = uploadFile(originalFileName, file.getBytes());
        log.debug("saved file name : {}", savedFileName);

        return "redirect:/";
    }

    // 파일명 랜덤생성 메서드
    private String uploadFile(String originalName, byte[] fileData) throws Exception {
        // uuid 생성(Universal Unique IDentifier, 범용 고유 식별자)
        UUID uuid = UUID.randomUUID();
        // 랜덤생성+파일이름 저장
        String savedName = uuid.toString() + "_" + originalName;
        File target = new File(filePath, savedName);
        // 임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
        // FileCopyUtils.copy(바이트배열, 파일객체)
        FileCopyUtils.copy(fileData, target);
        return savedName;
    }
}