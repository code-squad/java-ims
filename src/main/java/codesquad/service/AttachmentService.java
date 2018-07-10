package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// TODO 아무나 다운로드 받을 수 있는 경우
// TODO 해당 댓글의 요청인 경우도 구분?
// TODO 원래 제목 저장하지 않는다?

@Service
public class AttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    IssueRepository issueRepository;

    @Value("${file.path}")
    private String filePath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    // 파일명 랜덤생성 메서드
    public String makeNewName(String originalName) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "_" + originalName;
    }

    // 바이트 배열을 특정 이름으로 filePath에 저장되는 파일에 넣어서 리턴
    public File makeFile(byte[] fileData, String savedName) {
        File target = new File(filePath, savedName);
        try {
            FileCopyUtils.copy(fileData, target);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        return target;
    }

    // Attachment 객체 생성해서 repo에 저장하고 리턴
    public Attachment saveFile(File madeFile, String type, Long issueId) {
        Issue foundIssue = issueRepository.findById(issueId).orElseThrow(EntityNotFoundException::new);
        Attachment attachment = new Attachment(madeFile.getName(), type, foundIssue);
        return attachmentRepository.save(attachment);
    }

    public Attachment getAttachment(long id) {
        return attachmentRepository.getOne(id);
    }

    public ResponseEntity<PathResource> makeFileResponse(long id) {
        // DB에서 id에 해당하는 파일 경로 정보를 얻는다.
        Attachment attachment = getAttachment(id);

        // 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
        Path path = Paths.get("./files/" + attachment.getName());
        PathResource resource = new PathResource(path);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(attachment.getType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getName());
        try {
            header.setContentLength(resource.contentLength());
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}
